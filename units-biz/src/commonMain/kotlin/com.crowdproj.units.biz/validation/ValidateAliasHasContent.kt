package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.errorValidation
import com.crowdproj.units.common.helpers.fail

fun ICorAddExecDsl<MkplContext>.validateAliasHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { unitValidating.alias.isNotEmpty() && !unitValidating.alias.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "alias",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}