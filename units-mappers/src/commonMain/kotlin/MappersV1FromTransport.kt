package com.crowdproj.marketplace.units.mappers

import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.models.*
import com.crowdproj.marketplace.units.common.stubs.UntsStubs
import com.crowdproj.marketplace.units.mappers.exceptions.UnknownRequestClass

fun UntsContext.fromTransport(request: IUnitRequest) = when (request) {
    is UnitCreateRequest -> fromTransport(request)
    is UnitReadRequest -> fromTransport(request)
    is UnitUpdateRequest -> fromTransport(request)
    is UnitDeleteRequest -> fromTransport(request)
    is UnitSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

private fun String?.toUnitId() = this?.let { UntsUnitId(it) } ?: UntsUnitId.NONE
private fun IUnitRequest?.requestId() = this?.requestId?.let { UntsRequestId(it) } ?: UntsRequestId.NONE

private fun UnitDebug?.transportToWorkMode(): UntsWorkMode = when (this?.mode) {
    UnitRequestDebugMode.PROD -> UntsWorkMode.PROD
    UnitRequestDebugMode.TEST -> UntsWorkMode.TEST
    UnitRequestDebugMode.STUB -> UntsWorkMode.STUB
    null -> UntsWorkMode.PROD
}

private fun UnitDebug?.transportToStubCase(): UntsStubs = when (this?.stub) {
    UnitRequestDebugStubs.SUCCESS -> UntsStubs.SUCCESS
    UnitRequestDebugStubs.NOT_FOUND -> UntsStubs.NOT_FOUND
    UnitRequestDebugStubs.CANNOT_DELETE -> UntsStubs.CANNOT_DELETE
    UnitRequestDebugStubs.BAD_ID -> UntsStubs.BAD_ID
    UnitRequestDebugStubs.BAD_UNIT_ID -> UntsStubs.BAD_UNIT_ID
    UnitRequestDebugStubs.BAD_SYSTEM_UNIT_ID -> UntsStubs.BAD_SYSTEM_UNIT_ID
    UnitRequestDebugStubs.BAD_ALIAS -> UntsStubs.BAD_ALIAS
    UnitRequestDebugStubs.BAD_DESCRIPTION -> UntsStubs.BAD_DESCRIPTION
    UnitRequestDebugStubs.BAD_CONVERSION_PARAMETERS -> UntsStubs.BAD_CONVERSION_PARAMETERS
    UnitRequestDebugStubs.BAD_SEARCH_STRING -> UntsStubs.BAD_SEARCH_STRING
    null -> UntsStubs.NONE
}

fun UntsContext.fromTransport(request: UnitCreateRequest) {
    command = UntsCommand.CREATE
    requestId = request.requestId()
    unitRequest = request.params.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun UntsContext.fromTransport(request: UnitReadRequest) {
    command = UntsCommand.READ
    requestId = request.requestId()
    unitRequest = request.params.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun UntsContext.fromTransport(request: UnitUpdateRequest) {
    command = UntsCommand.UPDATE
    requestId = request.requestId()
    unitRequest = request.params.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun UntsContext.fromTransport(request: UnitDeleteRequest) {
    command = UntsCommand.DELETE
    requestId = request.requestId()
    unitRequest = request.params.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun UntsContext.fromTransport(request: UnitSearchRequest) {
    command = UntsCommand.SEARCH
    requestId = request.requestId()
    unitFilterRequest = request.params.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun UnitCreateParams.toInternal(): UntsUnit = UntsUnit(
    unit = this.unit,
    alias = this.alias,
    description = this.description ?: "",
    status = this.status.fromTransport(),
    systemUnitId = this.systemUnitId.toUnitId(),
    conversion = this.conversion.fromTransport()
)

private fun UnitReadParams.toInternal(): UntsUnit = UntsUnit(
    id = this.unitId.toUnitId()
)

private fun UnitUpdateParams.toInternal(): UntsUnit = UntsUnit(
    id = this.id.toUnitId(),
    unit = this.unit,
    alias = this.alias,
    description = this.description ?: "",
    status = this.status.fromTransport(),
    systemUnitId = this.systemUnitId.toUnitId(),
    conversion = this.conversion.fromTransport()
)

private fun UnitDeleteParams.toInternal(): UntsUnit = UntsUnit(
    id = this.unitId.toUnitId(),
    status = UntsUnitStatus.DELETED
)

private fun UnitSearchParams.toInternal(): UntsUnitFilter = UntsUnitFilter(
    searchString = this.unitFilter.search,
)

private fun UnitConversionParameters.fromTransport(): UntsConversionParameters = UntsConversionParameters(
    A = this.A,
    B = this.B,
    C = this.C,
    D = this.D
)

private fun UnitStatus.fromTransport(): UntsUnitStatus = when (this) {
    UnitStatus.SUGGESTED -> UntsUnitStatus.SUGGESTED
    UnitStatus.CONFIRMED -> UntsUnitStatus.CONFIRMED
    UnitStatus.DEPRECATED -> UntsUnitStatus.DEPRECATED
    UnitStatus.DELETED -> UntsUnitStatus.DELETED
    null -> UntsUnitStatus.NONE
}
