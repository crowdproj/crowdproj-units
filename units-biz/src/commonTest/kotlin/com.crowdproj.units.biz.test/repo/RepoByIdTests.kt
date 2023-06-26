package com.crowdproj.units.biz.test.repo

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.DbUnitResponse
import com.crowdproj.units.repo.tests.UnitRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

private val initUnit = MkplUnit(
    id = MkplUnitId("123"),
    name = "abc",
    description = "abc",
    alias = "ccc",
    status = MkplUnitStatus.DEPRECATED,
)
private val repo = UnitRepositoryMock(
    invokeReadUnit = {
        if (it.id == initUnit.id) {
            DbUnitResponse(
                isSuccess = true,
                data = initUnit,
            )
        } else DbUnitResponse(
            isSuccess = false,
            data = null,
            errors = listOf(MkplError(message = "Not found", field = "id"))
        )
    }
)
private val settings by lazy {
    MkplCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { MkplUnitProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: MkplCommand) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId("12345"),
            name = "xyz",
            description = "xyz",
            alias = "xyz",
            status = MkplUnitStatus.DEPRECATED,
            lock = MkplUnitLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(MkplState.FAILING, ctx.state)
    assertEquals(MkplUnit(), ctx.unitResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
