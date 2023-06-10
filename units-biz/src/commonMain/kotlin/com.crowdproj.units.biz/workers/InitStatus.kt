package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == MkplState.NONE }
    handle { state = MkplState.RUNNING }
}