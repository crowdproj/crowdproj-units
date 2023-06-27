package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.errorValidation
import com.crowdproj.units.common.helpers.fail

fun ICorAddExecDsl<MkplContext>.validateNameNotEmpty(title: String) = worker {
    this.title = title
    on { unitValidating.name.isEmpty() }
    handle {
        fail (
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}