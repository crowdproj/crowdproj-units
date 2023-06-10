package com.crowdproj.units.biz.test.stub

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UnitSuggestStubTest {

    private val processor = MkplUnitProcessor()
    private val name = "pieces"
    private val alias = "pcs"

    @Test
    fun suggest() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SUGGEST,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            unitRequest = MkplUnit(
                name = name,
                alias = alias
            ),
        )
        processor.exec(ctx)
        assertEquals(name, ctx.unitResponse.name)
        assertEquals(alias, ctx.unitResponse.alias)
        assertEquals(MkplUnitStatus.SUGGESTED, ctx.unitResponse.status)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SUGGEST,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SUGGEST,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}