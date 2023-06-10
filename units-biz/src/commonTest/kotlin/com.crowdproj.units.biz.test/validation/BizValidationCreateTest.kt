package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.models.MkplCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = MkplCommand.CREATE
    private val processor by lazy { MkplUnitProcessor() }

    @Test
    fun correctName() = validationNameCorrect(command, processor)

    @Test
    fun trimName() = validationNameTrim(command, processor)

    @Test
    fun emptyName() = validationNameEmpty(command, processor)

    @Test
    fun badSymbolsName() = validationNameSymbols(command, processor)

    @Test
    fun correctAlias() = validationAliasCorrect(command, processor)

    @Test
    fun trimAlias() = validationAliasTrim(command, processor)

    @Test
    fun emptyAlias() = validationAliasEmpty(command, processor)

    @Test
    fun badSymbolsAlias() = validationAliasSymbols(command, processor)

}