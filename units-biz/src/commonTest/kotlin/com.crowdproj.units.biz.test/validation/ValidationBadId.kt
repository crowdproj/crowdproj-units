package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            alias = "abc",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            description = "abc",
            alias = "abc",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId(""),
            name = "abc",
            description = "abc",
            alias = "abc",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: MkplCommand, processor: MkplUnitProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        unitRequest = MkplUnit(
            id = MkplUnitId("!@#\$%^&*(),.{}"),
            name = "abc",
            description = "abc",
            alias = "abc",
            status = MkplUnitStatus.CONFIRMED,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
