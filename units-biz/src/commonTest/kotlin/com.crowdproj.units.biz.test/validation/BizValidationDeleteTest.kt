package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.repo.stubs.UnitRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = MkplCommand.DELETE
    private val settings by lazy {
        MkplCorSettings(
            repoTest = UnitRepoStub()
        )
    }
    private val processor by lazy { MkplUnitProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun correctLock() = validationLockCorrect(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)
}