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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val command = MkplCommand.DELETE
    private val initUnit = MkplUnit(
        id = MkplUnitId("123"),
        name = "abc",
        description = "abc",
        alias = "ccc",
        status = MkplUnitStatus.SUGGESTED,
        lock = MkplUnitLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        UnitRepositoryMock(
            invokeReadUnit = {
                DbUnitResponse(
                    isSuccess = true,
                    data = initUnit,
                )
            },
            invokeDeleteUnit = {
                if (it.id == initUnit.id)
                    DbUnitResponse(
                        isSuccess = true,
                        data = initUnit.also { unit ->
                            unit.status = MkplUnitStatus.DELETED
                        }
                    )
                else DbUnitResponse(isSuccess = false, data = null)
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
    fun repoDeleteSuccessTest() = runTest {
        val unitToUpdate = MkplUnit(
            id = MkplUnitId("123"),
            lock = MkplUnitLock("123-234-abc-ABC"),
            status = MkplUnitStatus.DELETED,
        )
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitRequest = unitToUpdate,
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initUnit.id, ctx.unitResponse.id)
        assertEquals(initUnit.name, ctx.unitResponse.name)
        assertEquals(initUnit.description, ctx.unitResponse.description)
        assertEquals(initUnit.alias, ctx.unitResponse.alias)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}