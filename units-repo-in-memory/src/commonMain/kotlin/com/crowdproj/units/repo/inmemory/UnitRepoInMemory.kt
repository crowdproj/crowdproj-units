package com.crowdproj.units.repo.inmemory

import com.benasher44.uuid.uuid4
import com.crowdproj.units.common.helpers.errorRepoConcurrency
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.*
import com.crowdproj.units.repo.inmemory.model.UnitEntity
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class UnitRepoInMemory(
    initObjects: List<MkplUnit> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() }
) : IUnitRepository {

    private val cache = Cache.Builder<String, UnitEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(unit: MkplUnit) {
        val entity = UnitEntity(unit)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = randomUuid()
        val unit = rq.unit.copy(id = MkplUnitId(key), lock = MkplUnitLock(randomUuid()))
        val entity = UnitEntity(unit)
        cache.put(key, entity)
        return DbUnitResponse(
            data = unit,
            isSuccess = true,
        )
    }

    override suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse {
        val key = rq.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbUnitResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = rq.unit.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.unit.lock.takeIf { it != MkplUnitLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newUnit = rq.unit.copy(lock = MkplUnitLock(randomUuid()))
        val entity = UnitEntity(newUnit)
        return mutex.withLock {
            val oldUnit = cache.get(key)
            when {
                oldUnit == null -> resultErrorNotFound
                oldUnit.lock != oldLock -> DbUnitResponse(
                    data = oldUnit.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(MkplUnitLock(oldLock), oldUnit.lock?.let { MkplUnitLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbUnitResponse(
                        data = newUnit,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse {
        val key = rq.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != MkplUnitLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldUnit = cache.get(key)
            when {
                oldUnit == null -> resultErrorNotFound
                oldUnit.lock != oldLock -> DbUnitResponse(
                    data = oldUnit.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(MkplUnitLock(oldLock), oldUnit.lock?.let { MkplUnitLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbUnitResponse(
                        data = oldUnit.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.nameFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .filter { entry ->
                rq.unitId.takeIf { it != MkplUnitId.NONE }?.let {
                    it.asString() == entry.value.id
                } ?: true
            }
            .filter { entry ->
                rq.unitStatus.takeIf { it != MkplUnitStatus.NONE }?.let {
                    it.name == entry.value.status
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbUnitsResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = randomUuid()
        val unit = rq.unit.copy(id = MkplUnitId(key), lock = MkplUnitLock(randomUuid()))
        val entity = UnitEntity(unit)
        cache.put(key, entity)
        return DbUnitResponse(
            data = unit,
            isSuccess = true,
        )
    }

    companion object {
        val resultErrorEmptyId = DbUnitResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                MkplError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbUnitResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                MkplError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbUnitResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                MkplError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}