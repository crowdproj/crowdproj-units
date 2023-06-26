package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.repo.DbUnitIdRequest
import com.crowdproj.units.common.repo.IUnitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitReadTest {
    abstract val repo: IUnitRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readUnit(DbUnitIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readUnit(DbUnitIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitUnits("delete") {
        override val initObjects: List<MkplUnit> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = MkplUnitId("unit-repo-read-notFound")

    }
}