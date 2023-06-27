package com.crowdproj.units.repo.gremlin

import com.benasher44.uuid.uuid4
import com.crowdproj.units.common.helpers.asMkplError
import com.crowdproj.units.common.helpers.errorAdministration
import com.crowdproj.units.common.helpers.errorRepoConcurrency
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.*
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_ID
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_LOCK
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_NAME
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_STATUS
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.RESULT_LOCK_FAILURE
import com.crowdproj.units.repo.gremlin.exceptions.DbDuplicatedElementsException
import com.crowdproj.units.repo.gremlin.mappers.addMkplUnit
import com.crowdproj.units.repo.gremlin.mappers.label
import com.crowdproj.units.repo.gremlin.mappers.listMkplUnit
import com.crowdproj.units.repo.gremlin.mappers.toMkplUnit
import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.exception.ResponseException
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource
import org.apache.tinkerpop.gremlin.process.traversal.TextP
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import org.apache.tinkerpop.gremlin.structure.Vertex

class UnitRepoGremlin(
    properties: GremlinProperties,
    initObjects: List<MkplUnit> = emptyList(),
    initRepo: ((GraphTraversalSource) -> Unit)? = null,
    val randomUuid: () -> String = { uuid4().toString() },
) : IUnitRepository {
    val initializedObjects: List<MkplUnit>

    private val cluster by lazy {
        Cluster.build().apply {
            addContactPoints(*properties.hosts.split(Regex("\\s*,\\s*")).toTypedArray())
            port(properties.port)
            credentials(properties.user, properties.pass)
            enableSsl(properties.enableSsl)
        }.create()
    }
    private val g by lazy { AnonymousTraversalSource.traversal().withRemote(DriverRemoteConnection.using(cluster)) }

    init {
        if (initRepo != null) {
            initRepo(g)
        }
        initializedObjects = initObjects.map { save(it) }
    }

    private fun save(unit: MkplUnit): MkplUnit = g.addV(unit.label())
        .addMkplUnit(unit)
        .listMkplUnit()
        .next()
        ?.toMkplUnit()
        ?: throw RuntimeException("Cannot initialize object $unit")

    override suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = randomUuid()
        val unit = rq.unit.copy(id = MkplUnitId(key), lock = MkplUnitLock(randomUuid()))
        val dbRes = try {
            g.addV(unit.label())
                .addMkplUnit(unit)
                .listMkplUnit()
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbUnitResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asMkplError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbUnitResponse(
                data = dbRes.first().toMkplUnit(),
                isSuccess = true,
            )

            else -> errorDuplication(key)
        }
    }

    override suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse {
        val key = rq.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        val dbRes = try {
            g.V(key).listMkplUnit().toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbUnitResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asMkplError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbUnitResponse(
                data = dbRes.first().toMkplUnit(),
                isSuccess = true,
            )

            else -> errorDuplication(key)
        }
    }

    override suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = rq.unit.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.unit.lock.takeIf { it != MkplUnitLock.NONE } ?: return resultErrorEmptyLock
        val newLock = MkplUnitLock(randomUuid())
        val newUnit = rq.unit.copy(lock = newLock)
        val dbRes = try {
            g
                .V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Any>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a").addMkplUnit(newUnit).listMkplUnit(),
                    gr.select<Vertex, Vertex>("a").listMkplUnit(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbUnitResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asMkplError())
            )
        }
        val unitResult = dbRes.firstOrNull()?.toMkplUnit()
        return when {
            unitResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            unitResult.lock != newLock -> DbUnitResponse(
                data = unitResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = unitResult.lock,
                    ),
                )
            )

            else -> DbUnitResponse(
                data = unitResult,
                isSuccess = true,
            )
        }
    }

    override suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse {
        val key = rq.id.takeIf { it != MkplUnitId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != MkplUnitLock.NONE } ?: return resultErrorEmptyLock
        val dbRes = try {
            g
                .V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Vertex>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a")
                        .sideEffect(gr.drop<Vertex>())
                        .listMkplUnit(),
                    gr.select<Vertex, Vertex>("a")
                        .listMkplUnit(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbUnitResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asMkplError())
            )
        }
        val dbFirst = dbRes.firstOrNull()
        val unitResult = dbFirst?.toMkplUnit()
        return when {
            unitResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            dbFirst[FIELD_TMP_RESULT] == RESULT_LOCK_FAILURE -> DbUnitResponse(
                data = unitResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = unitResult.lock,
                    ),
                )
            )

            else -> DbUnitResponse(
                data = unitResult,
                isSuccess = true,
            )
        }
    }

    override suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse {
        val result = try {
            g.V()
                .apply { rq.unitId.takeIf { it != MkplUnitId.NONE }?.also { has(FIELD_ID, it.asString()) } }
                .apply { rq.unitStatus.takeIf { it != MkplUnitStatus.NONE }?.also { has(FIELD_STATUS, it.name) } }
                .apply {
                    rq.nameFilter.takeIf { it.isNotBlank() }?.also { has(FIELD_NAME, TextP.containing(it)) }
                }
                .listMkplUnit()
                .toList()
        } catch (e: Throwable) {
            return DbUnitsResponse(
                isSuccess = false,
                data = null,
                errors = listOf(e.asMkplError())
            )
        }
        return DbUnitsResponse(
            data = result.map { it.toMkplUnit() },
            isSuccess = true
        )
    }

    override suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse {
        val key = randomUuid()
        val unit = rq.unit.copy(id = MkplUnitId(key), lock = MkplUnitLock(randomUuid()))
        val dbRes = try {
            g.addV(unit.label())
                .addMkplUnit(unit.apply { this.status = MkplUnitStatus.SUGGESTED })
                .listMkplUnit()
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbUnitResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asMkplError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbUnitResponse(
                data = dbRes.first().toMkplUnit(),
                isSuccess = true,
            )

            else -> errorDuplication(key)
        }
    }

    companion object {
        val resultErrorEmptyId = DbUnitResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                MkplError(
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
                    field = "lock",
                    message = "Lock must be provided"
                )
            )
        )

        fun resultErrorNotFound(key: String, e: Throwable? = null) = DbUnitResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                MkplError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found object with key $key",
                    exception = e
                )
            )
        )

        fun errorDuplication(key: String) = DbUnitResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                errorAdministration(
                    violationCode = "duplicateObjects",
                    description = "Database consistency failure",
                    exception = DbDuplicatedElementsException("Db contains multiple elements for id = '$key'")
                )
            )
        )
    }
}