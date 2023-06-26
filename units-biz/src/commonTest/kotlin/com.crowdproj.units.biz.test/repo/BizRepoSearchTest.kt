package com.crowdproj.units.biz.test.repo

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.DbUnitsResponse
import com.crowdproj.units.repo.tests.UnitRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val command = MkplCommand.SEARCH
    private val initUnit = MkplUnit(
        id = MkplUnitId("123"),
        name = "abc",
        alias = "ccc",
        status = MkplUnitStatus.CONFIRMED
    )
    private val repo by lazy { UnitRepositoryMock(
        invokeSearchUnit = {
            DbUnitsResponse(
                isSuccess = true,
                data = listOf(initUnit),
            )
        }
    ) }
    private val settings by lazy {
        MkplCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplUnitProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitFilterRequest = MkplUnitFilter(
                searchString = "ab",
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertEquals(1, ctx.unitsResponse.size)
    }
}