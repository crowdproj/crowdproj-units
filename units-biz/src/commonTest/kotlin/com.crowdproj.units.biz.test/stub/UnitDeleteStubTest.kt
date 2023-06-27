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
class UnitDeleteStubTest {

    private val processor = MkplUnitProcessor()
    private val id = MkplUnitId("321")

    @Test
    fun delete() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.DELETE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            unitRequest = MkplUnit(
                id = id
            )
        )
        processor.exec(ctx)
        println(ctx.unitResponse)
        val stub = MkplUnitStub.get()
        assertEquals(id, ctx.unitResponse.id)
        assertEquals(stub.name, ctx.unitResponse.name)
        assertEquals(stub.description, ctx.unitResponse.description)
        assertEquals(stub.alias, ctx.unitResponse.alias)
    }

    @Test
    fun badId() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.DELETE,
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
            command = MkplCommand.DELETE,
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
            command = MkplCommand.DELETE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            unitRequest = MkplUnit(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}