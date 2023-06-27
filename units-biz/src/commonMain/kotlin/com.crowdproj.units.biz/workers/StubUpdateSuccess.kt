package com.crowdproj.units.biz.workers

import MkplUnitStub
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.common.stubs.MkplStubs

fun ICorAddExecDsl<MkplContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING && stubCase == MkplStubs.SUCCESS }
    handle {
        state = MkplState.FINISHING
        val stub = MkplUnitStub.prepareResult {
            unitRequest.id.takeIf { it != MkplUnitId.NONE }?.also { this.id = it }
            unitRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            unitRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            unitRequest.alias.takeIf { it.isNotBlank() }?.also { this.alias = it }
            unitRequest.status.takeIf { it != MkplUnitStatus.NONE }?.also { this.status = it }
        }

        unitResponse = stub
    }
}