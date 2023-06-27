package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.common.repo.DbUnitRequest
import com.crowdproj.units.common.repo.IUnitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUnitSuggestTest {
    abstract val repo: IUnitRepository
    protected open val suggestSucc = initObjects[0]
    protected open val suggestConc = initObjects[1]
    protected val lockNew = MkplUnitLock("20000000-0000-0000-0000-000000000002")

    private val reqSuggestSucc by lazy {
        MkplUnit(
            id = suggestSucc.id,
            name = "suggest object",
            alias = "suggest alias",
            description = "suggest object description",
            status = MkplUnitStatus.SUGGESTED,
            lock = initObjects.first().lock,
        )
    }

    @Test
    fun suggestSuccess() = runRepoTest {
        val result = repo.suggestUnit(DbUnitRequest(reqSuggestSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqSuggestSucc.name, result.data?.name)
        assertEquals(reqSuggestSucc.description, result.data?.description)
        assertEquals(reqSuggestSucc.alias, result.data?.alias)
        assertEquals(reqSuggestSucc.status, result.data?.status)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitUnits("suggest") {
        override val initObjects: List<MkplUnit> = listOf(
            createInitTestModel("suggest"),
            createInitTestModel("suggestConc"),
        )
    }
}