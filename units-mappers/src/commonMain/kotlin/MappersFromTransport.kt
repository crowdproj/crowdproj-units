package com.crowdproj.units.mappers

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
import com.crowdproj.units.mappers.exceptions.UnknownRequestClass

fun MkplContext.fromTransport(request: UnitRequest) = when (request) {
    is UnitCreateRequest -> fromTransport(request)
    is UnitReadRequest -> fromTransport(request)
    is UnitUpdateRequest -> fromTransport(request)
    is UnitDeleteRequest -> fromTransport(request)
    is UnitSearchRequest -> fromTransport(request)
    is UnitSuggestRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

private fun String?.toUnitId() = this?.let { MkplUnitId(it) } ?: MkplUnitId.NONE
private fun String?.toUnitWithId() = MkplUnit(id = this.toUnitId())
private fun UnitRequest?.requestId() = this?.requestId?.let { MkplRequestId(it) } ?: MkplRequestId.NONE

private fun UnitDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    UnitRequestDebugMode.PROD -> MkplWorkMode.PROD
    UnitRequestDebugMode.TEST -> MkplWorkMode.TEST
    UnitRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun UnitDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    UnitRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    UnitRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    UnitRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    UnitRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    UnitRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    UnitRequestDebugStubs.BAD_SUGGEST_STRING -> MkplStubs.BAD_SUGGEST_STRING
    null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: UnitCreateRequest) {
    command = MkplCommand.CREATE
    requestId = request.requestId()
    unitRequest = request.unit?.toInternal() ?: MkplUnit()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: UnitReadRequest) {
    command = MkplCommand.READ
    requestId = request.requestId()
    unitRequest = request.unit?.id.toUnitWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: UnitUpdateRequest) {
    command = MkplCommand.UPDATE
    requestId = request.requestId()
    unitRequest = request.unit?.toInternal() ?: MkplUnit()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: UnitDeleteRequest) {
    command = MkplCommand.DELETE
    requestId = request.requestId()
    unitRequest = request.unit?.toInternal() ?: MkplUnit()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: UnitSearchRequest) {
    command = MkplCommand.SEARCH
    requestId = request.requestId()
    unitFilterRequest = request.unitFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: UnitSuggestRequest) {
    command = MkplCommand.SUGGEST
    requestId = request.requestId()
    unitRequest = request.unit?.toInternal() ?: MkplUnit()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun UnitSearchFilter?.toInternal(): MkplUnitFilter = MkplUnitFilter(
    searchString = this?.searchString ?: ""
)

private fun UnitCreateObject.toInternal(): MkplUnit = MkplUnit(
    name = this.name ?: "",
    alias = this.alias ?: "",
    description = this.description ?: "",
    status = this.status.fromTransport(),
//    systemUnitId = this.systemUnitId.fromTransport()
)

private fun UnitDeleteObject.toInternal(): MkplUnit = MkplUnit(
    id = this.id.toUnitId(),
    status = MkplUnitStatus.DELETED
)

private fun UnitUpdateObject.toInternal(): MkplUnit = MkplUnit(
    id = this.id.toUnitId(),
    name = this.name ?: "",
    alias = this.alias ?: "",
    description = this.description ?: "",
    status = this.status.fromTransport(),
//    systemUnitId = this.systemUnitId.fromTransport()
)

private fun UnitSuggestObject.toInternal(): MkplUnit = MkplUnit(
    name = this.name ?: "",
    alias = this.alias ?: "",
    description = this.description ?: "",
    status = MkplUnitStatus.SUGGESTED
)

private fun UnitStatus?.fromTransport(): MkplUnitStatus = when (this) {
    UnitStatus.SUGGESTED -> MkplUnitStatus.SUGGESTED
    UnitStatus.CONFIRMED -> MkplUnitStatus.CONFIRMED
    UnitStatus.DEPRECATED -> MkplUnitStatus.DEPRECATED
    UnitStatus.DELETED -> MkplUnitStatus.DELETED
    UnitStatus.NONE -> MkplUnitStatus.NONE
    null -> MkplUnitStatus.NONE
}

