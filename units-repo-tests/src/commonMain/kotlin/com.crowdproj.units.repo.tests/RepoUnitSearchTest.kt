package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.common.repo.DbUnitFilterRequest
import com.crowdproj.units.common.repo.IUnitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitSearchTest {
    abstract val repo: IUnitRepository

    protected open val initializedObjects: List<MkplUnit> = initObjects

    @Test
    fun searchAlias() = runRepoTest {
        val result = repo.searchUnit(DbUnitFilterRequest(nameFilter = searchName))
        assertEquals(true, result.isSuccess)

        val expected = listOf(initializedObjects[0], initializedObjects[1]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchStatusAndName() = runRepoTest {
        val result = repo.searchUnit(DbUnitFilterRequest(nameFilter = searchName, unitStatus = MkplUnitStatus.CONFIRMED))
        assertEquals(true, result.isSuccess)

        val expected = listOf(initializedObjects[0], initializedObjects[1]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitUnits("search") {

        const val searchName = "millimeter"
        override val initObjects: List<MkplUnit> = listOf(
            createInitTestModel("unit1", name = "millimeter", status = MkplUnitStatus.CONFIRMED),
            createInitTestModel("unit2", name = "millimeter", status = MkplUnitStatus.CONFIRMED),
            createInitTestModel("unit3"),
            createInitTestModel("unit4"),
            createInitTestModel("unit5"),
        )
    }
}