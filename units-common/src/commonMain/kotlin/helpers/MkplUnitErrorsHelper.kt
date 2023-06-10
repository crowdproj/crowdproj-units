package com.crowdproj.units.common.helpers

import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplState

fun Throwable.asMkplError(
    code: String = "unknowm",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkplError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun MkplContext.addError(vararg error: MkplError) = errors.addAll(error)

fun MkplContext.fail(error: MkplError) {
    addError(error)
    state = MkplState.FAILING
}

fun errorValidation(
    field: String,
    violationCode: String,  // e.g. "empty", "badFormat", "noContent"
    description: String,
    level: MkplError.Level = MkplError.Level.ERROR,
) = MkplError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)