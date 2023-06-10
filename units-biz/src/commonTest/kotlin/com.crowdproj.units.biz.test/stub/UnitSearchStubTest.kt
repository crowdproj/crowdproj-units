package com.crowdproj.units.biz.test.stub

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class UnitSearchStubTest {

    private val processor = MkplUnitProcessor()
    private val filter = MkplUnitFilter(searchString = "Ampere")

    @Test
    fun create() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            unitFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.unitsResponse.size > 1)
        val first = ctx.unitsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (MkplUnitStub.get()) {
            assertEquals(status, first.status)
            assertEquals(systemUnitId, first.systemUnitId)
        }
    }

    @Test
    fun badSearchString() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_SEARCH_STRING,
            unitFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("search-string", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            unitFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            unitFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplUnit(), ctx.unitResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}