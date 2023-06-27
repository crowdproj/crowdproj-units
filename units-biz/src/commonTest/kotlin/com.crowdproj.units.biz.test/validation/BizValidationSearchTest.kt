package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplUnitFilter
import com.crowdproj.units.common.models.MkplWorkMode
import com.crowdproj.units.repo.stubs.UnitRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = MkplCommand.SEARCH
    private val settings by lazy {
        MkplCorSettings(
            repoTest = UnitRepoStub()
        )
    }
    private val processor by lazy { MkplUnitProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitFilterRequest = MkplUnitFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(MkplState.FAILING, ctx.state)
    }
}

