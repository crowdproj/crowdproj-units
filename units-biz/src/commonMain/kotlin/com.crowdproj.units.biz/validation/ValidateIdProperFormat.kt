package com.crowdproj.units.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.errorValidation
import com.crowdproj.units.common.helpers.fail
import com.crowdproj.units.common.models.MkplUnitId

fun ICorAddExecDsl<MkplContext>.validateIdProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+\$")
    on { unitValidating.id != MkplUnitId.NONE && ! unitValidating.id.asString().matches(regExp) }
    handle {
        /**
         * help prevent XSS attacks be encoding special HTML symbols
         */
        val encodedId = unitValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}