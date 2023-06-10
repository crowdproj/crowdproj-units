package com.crowdproj.units.biz.groups

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplWorkMode

fun ICorAddExecDsl<MkplContext>.stubs(title: String, block: ICorAddExecDsl<MkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == MkplWorkMode.STUB && state == MkplState.RUNNING }
}