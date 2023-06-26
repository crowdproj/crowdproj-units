package com.crowdproj.units.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplWorkMode

fun ICorAddExecDsl<MkplContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Preparing data to put to response"
    on { workMode != MkplWorkMode.STUB }
    handle {
        unitResponse = unitRepoDone
        unitsResponse = unitsRepoDone
        state = when (val st = state) {
            MkplState.RUNNING -> MkplState.FINISHING
            else -> st
        }
    }
}