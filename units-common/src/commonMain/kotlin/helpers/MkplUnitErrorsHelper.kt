package com.crowdproj.units.common.helpers

import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.exceptions.RepoConcurrencyException
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplUnitLock

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

fun errorAdministration(
    field: String = "",
    violationCode: String, // e.g. "empty", "badFormat", "noContent"
    description: String,
    exception: Exception? = null,
    level: MkplError.Level = MkplError.Level.ERROR,
) = MkplError(
    code = "administration-$violationCode",
    field = field,
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: MkplUnitLock,
    actualLock: MkplUnitLock?,
    exception: Exception? = null,
) = MkplError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock)
)

val errorNotFound = MkplError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = MkplError(
    field = "id",
    message = "Id must not be null or blank"
)