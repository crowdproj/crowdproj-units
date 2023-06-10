package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.common.stubs.MkplStubs

fun ICorAddExecDsl<MkplContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    handle {
        state = MkplState.FINISHING
        val stub = MkplUnitStub.prepareResult {
            unitRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            unitRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            unitRequest.alias.takeIf { it.isNotBlank() }?.also { this.alias = it }
            unitRequest.status.takeIf { it != MkplUnitStatus.NONE }?.also { this.status = it }
        }
        unitResponse = stub
    }
}