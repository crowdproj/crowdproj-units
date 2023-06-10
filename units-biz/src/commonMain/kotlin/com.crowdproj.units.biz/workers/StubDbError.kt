package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.stubs.MkplStubs

fun ICorAddExecDsl<MkplContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.DB_ERROR && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        errors.add(
            MkplError(
                group = "internal",
                code = "internal-db",
                message = "Internal-db"
            )
        )
    }
}