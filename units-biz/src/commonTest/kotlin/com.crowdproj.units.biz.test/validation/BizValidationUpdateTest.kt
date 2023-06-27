package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.repo.stubs.UnitRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = MkplCommand.UPDATE
    private val settings by lazy {
        MkplCorSettings(
            repoTest = UnitRepoStub()
        )
    }
    private val processor by lazy { MkplUnitProcessor(settings) }

    @Test
    fun correctTitle() = validationNameCorrect(command, processor)
    @Test
    fun trimTitle() = validationNameTrim(command, processor)
    @Test
    fun emptyTitle() = validationNameEmpty(command, processor)
    @Test
    fun badSymbolsTitle() = validationNameSymbols(command, processor)

    @Test
    fun correctDescription() = validationAliasCorrect(command, processor)
    @Test
    fun trimDescription() = validationAliasTrim(command, processor)
    @Test
    fun emptyDescription() = validationAliasEmpty(command, processor)
    @Test
    fun badSymbolsDescription() = validationAliasSymbols(command, processor)

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)

    @Test
    fun correctLock() = validationLockCorrect(command, processor)
    @Test
    fun trimLock() = validationLockTrim(command, processor)
    @Test
    fun emptyLock() = validationLockEmpty(command, processor)
    @Test
    fun badFormatLock() = validationLockFormat(command, processor)

}
