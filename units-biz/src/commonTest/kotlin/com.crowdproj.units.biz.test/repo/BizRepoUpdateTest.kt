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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val command = MkplCommand.UPDATE
    private val initUnit = MkplUnit(
        id = MkplUnitId("123"),
        name = "abc",
        description = "abc",
        alias = "ccc",
        status = MkplUnitStatus.CONFIRMED,
    )
    private val repo by lazy { UnitRepositoryMock(
        invokeReadUnit = {
            DbUnitResponse(
                isSuccess = true,
                data = initUnit,
            )
        },
        invokeUpdateUnit = {
            DbUnitResponse(
                isSuccess = true,
                data = MkplUnit(
                    id = MkplUnitId("123"),
                    name = "xyz",
                    description = "xyz",
                    alias = "xyz",
                    status = MkplUnitStatus.CONFIRMED,
                )
            )
        }
    )
}
    private val settings by lazy {
        MkplCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplUnitProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val unitToUpdate = MkplUnit(
            id = MkplUnitId("123"),
            name = "xyz",
            description = "xyz",
            alias = "xyz",
            status = MkplUnitStatus.CONFIRMED,
            lock = MkplUnitLock("123-234-abc-ABC")
        )
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitRequest = unitToUpdate,
        )
        processor.exec(ctx)
        println(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertEquals(unitToUpdate.id, ctx.unitResponse.id)
        assertEquals(unitToUpdate.name, ctx.unitResponse.name)
        assertEquals(unitToUpdate.description, ctx.unitResponse.description)
        assertEquals(unitToUpdate.alias, ctx.unitResponse.alias)
        assertEquals(unitToUpdate.status, ctx.unitResponse.status)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}