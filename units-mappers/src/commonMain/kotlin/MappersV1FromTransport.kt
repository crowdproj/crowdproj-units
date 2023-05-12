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

private fun CpBaseDebug?.transportToWorkMode(): UntsWorkMode = when (this?.mode) {
    CpRequestDebugMode.PROD -> UntsWorkMode.PROD
    CpRequestDebugMode.TEST -> UntsWorkMode.TEST
    CpRequestDebugMode.STUB -> UntsWorkMode.STUB
    null -> UntsWorkMode.PROD
}

private fun CpBaseDebug?.transportToStubCase(): UntsStubs = when (this?.stub) {
    CpRequestDebugStubs.SUCCESS -> UntsStubs.SUCCESS
    CpRequestDebugStubs.NOT_FOUND -> UntsStubs.NOT_FOUND
    CpRequestDebugStubs.BAD_ID -> UntsStubs.BAD_ID
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
)

private fun UnitStatus.fromTransport(): UntsUnitStatus = when (this) {
    UnitStatus.SUGGESTED -> UntsUnitStatus.SUGGESTED
    UnitStatus.CONFIRMED -> UntsUnitStatus.CONFIRMED
    UnitStatus.DEPRECATED -> UntsUnitStatus.DEPRECATED
    UnitStatus.DELETED -> UntsUnitStatus.DELETED
}
