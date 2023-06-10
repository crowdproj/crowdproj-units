package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MkplUnitStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAliasCorrect(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = stub.id,
            name = "abc",
            alias = "abc",
            description = "abc",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAliasTrim(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = stub.id,
            name = "abc",
            description = "abc",
            alias = " \n\t abc \t\n ",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.unitValidated.alias)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAliasEmpty(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = stub.id,
            name = "abc",
            description = "abc",
            alias = "",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("alias", error?.field)
    assertContains(error?.message ?: "", "alias")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAliasSymbols(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId("123"),
            name = "abc",
            description = "abc",
            alias = "!@#\$%^&*(),.{}",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("alias", error?.field)
    assertContains(error?.message ?: "", "alias")
}