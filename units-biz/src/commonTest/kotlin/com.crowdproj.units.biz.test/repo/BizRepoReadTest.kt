package com.crowdproj.units.biz.test.repo

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.DbUnitResponse
import com.crowdproj.units.repo.tests.UnitRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val command = MkplCommand.READ
    private val initUnit = MkplUnit(
        id = MkplUnitId("123"),
        name = "abc",
        description = "abc",
        alias = "abc",
        status = MkplUnitStatus.SUGGESTED,
    )
    private val repo by lazy { UnitRepositoryMock(
        invokeReadUnit = {
            DbUnitResponse(
                isSuccess = true,
                data = initUnit,
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
    fun repoReadSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitRequest = MkplUnit(
                id = MkplUnitId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertEquals(initUnit.id, ctx.unitResponse.id)
        assertEquals(initUnit.name, ctx.unitResponse.name)
        assertEquals(initUnit.description, ctx.unitResponse.description)
        assertEquals(initUnit.alias, ctx.unitResponse.alias)
        assertEquals(initUnit.status, ctx.unitResponse.status)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}