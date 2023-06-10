package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.validation(block: ICorAddExecDsl<MkplContext>.() -> Unit) = chain {
    block()
    title = "validation"

    on { state == MkplState.RUNNING }
}