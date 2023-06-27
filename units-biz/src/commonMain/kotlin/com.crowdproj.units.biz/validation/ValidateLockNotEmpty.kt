package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.errorValidation
import com.crowdproj.units.common.helpers.fail

fun ICorAddExecDsl<MkplContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title

    on { unitValidating.lock.asString().isEmpty() }
    handle {

        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}