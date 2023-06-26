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
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitCreateTest {
    abstract val repo: IUnitRepository

    protected open val lockNew: MkplUnitLock = MkplUnitLock("20000000-0000-0000-0000-000000000002")

    private val createObj = MkplUnit(
        name = "create object",
        alias = "create object alias",
        description = "create object description",
        status = MkplUnitStatus.CONFIRMED,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createUnit(DbUnitRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: MkplUnitId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.alias, result.data?.alias)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.status, result.data?.status)

        assertNotEquals(MkplUnitId.NONE, result.data?.id)

        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitUnits("create") {
        override val initObjects: List<MkplUnit> = emptyList()
    }

}