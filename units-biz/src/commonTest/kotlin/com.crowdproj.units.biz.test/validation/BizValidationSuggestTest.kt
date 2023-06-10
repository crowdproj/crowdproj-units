package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.models.MkplCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSuggestTest {

    private val command = MkplCommand.UPDATE
    private val processor by lazy { MkplUnitProcessor() }

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
}