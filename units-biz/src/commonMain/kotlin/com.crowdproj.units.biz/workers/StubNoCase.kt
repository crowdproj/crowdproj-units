package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.fail
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case requested: ${stubCase.name}"
            )
        )
    }
}