package com.crowdproj.units.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.operation(title: String, command: MkplCommand, block: ICorAddExecDsl<MkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == MkplState.RUNNING }
}