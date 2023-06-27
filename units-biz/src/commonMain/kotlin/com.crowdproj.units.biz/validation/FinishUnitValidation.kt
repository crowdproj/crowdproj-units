package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.finishUnitValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        unitValidated = unitValidating
    }
}

fun ICorAddExecDsl<MkplContext>.finishUnitFilterValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        unitFilterValidated = unitFilterValidating
    }
}