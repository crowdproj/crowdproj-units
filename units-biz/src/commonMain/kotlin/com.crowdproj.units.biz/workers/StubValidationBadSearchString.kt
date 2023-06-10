package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.stubs.MkplStubs

fun ICorAddExecDsl<MkplContext>.stubValidationBadSearchString(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING && stubCase == MkplStubs.BAD_SEARCH_STRING }
    handle {
        state = MkplState.FAILING
        errors.add(
            MkplError(
                group = "validation",
                code = "validation-search-string",
                field = "search-string",
                message = "Wrong search string",
            )
        )
    }
}