package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.repo.DbUnitIdRequest
import com.crowdproj.units.common.repo.IUnitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitDeleteTest {
    abstract val repo: IUnitRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = MkplUnitId("unit-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteUnit(DbUnitIdRequest(deleteSucc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readUnit(DbUnitIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteUnit(DbUnitIdRequest(deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitUnits("delete") {
        override val initObjects: List<MkplUnit> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}