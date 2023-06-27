package com.crowdproj.units.biz.test.stub

import MkplUnitStub
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UnitCreateStubTest {

    private val processor = MkplUnitProcessor()
    private val id = MkplUnitId("123")
    private val name = "Mile"
    private val description = "A unit of length measurement"
    private val alias = "M"
    private val status = MkplUnitStatus.CONFIRMED

    @Test
    fun create() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            unitRequest = MkplUnit(
                id = id,
                name = name,
                description = description,
                alias = alias,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplUnitStub.get().id, ctx.unitResponse.id)
        assertEquals(name, ctx.unitResponse.name)
        assertEquals(description, ctx.unitResponse.description)
        assertEquals(alias, ctx.unitResponse.alias)
        assertEquals(status, ctx.unitResponse.status)
    }

    @Test
    fun badId() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_ID,
            unitRequest = MkplUnit(),
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            unitRequest = MkplUnit(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            unitRequest = MkplUnit(
                id = id,
                name = name,
                description = description,
                alias = alias,
                status = status,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

}