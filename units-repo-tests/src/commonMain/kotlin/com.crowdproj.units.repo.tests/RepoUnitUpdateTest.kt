package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.common.repo.DbUnitRequest
import com.crowdproj.units.common.repo.IUnitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitUpdateTest {
    abstract val repo: IUnitRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = MkplUnitId("unit-repo-update-not-found")
    protected val lockBad = MkplUnitLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = MkplUnitLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        MkplUnit(
            id = updateSucc.id,
            name = "update object",
            alias = "update alias",
            description = "update object description",
            status = MkplUnitStatus.SUGGESTED,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = MkplUnit(
        id = updateIdNotFound,
        name = "update object not found",
        alias = "update object not found alias",
        description = "update object not found description",
        status = MkplUnitStatus.SUGGESTED,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        MkplUnit(
            id = updateConc.id,
            name = "update object not found",
            alias = "update object not found alias",
            description = "update object not found description",
            status = MkplUnitStatus.SUGGESTED,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateUnit(DbUnitRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.alias, result.data?.alias)
        assertEquals(reqUpdateSucc.status, result.data?.status)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateUnit(DbUnitRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateUnit(DbUnitRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitUnits("update") {
        override val initObjects: List<MkplUnit> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}