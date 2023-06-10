package com.crowdproj.units.biz.test.validation

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.models.MkplCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = MkplCommand.DELETE
    private val processor by lazy { MkplUnitProcessor() }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)
}