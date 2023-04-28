package com.crowdproj.units.mappers

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.mappers.exceptions.UnknownMkplCommand

fun MkplContext.toTransportUnit(): IUnitResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.SUGGEST -> toTransportSuggest()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportCreate() = UnitCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransportUnit()
)

fun MkplContext.toTransportRead() = UnitReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransportUnit()
)

fun MkplContext.toTransportUpdate() = UnitUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransportUnit()
)

fun MkplContext.toTransportDelete() = UnitDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransportUnit()
)

fun MkplContext.toTransportSearch() = UnitSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    units = unitsResponse.toTransportUnit()
)

fun MkplContext.toTransportSuggest() = UnitSuggestResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransportUnit()
)

fun List<MkplUnit>.toTransportUnit(): List<UnitResponseObject>? = this
    .map { it.toTransportUnit() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MkplContext.toTransportInit() = UnitInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun MkplUnit.toTransportUnit(): UnitResponseObject = UnitResponseObject(
    id = id.takeIf { it != MkplUnitId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    alias = alias.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    status = status.toTransportUnit(),
//    systemUnitId =
)

private fun MkplUnitStatus?.toTransportUnit() = when (this) {
    MkplUnitStatus.SUGGESTED -> UnitStatus.SUGGESTED
    MkplUnitStatus.CONFIRMED -> UnitStatus.CONFIRMED
    MkplUnitStatus.DEPRECATED -> UnitStatus.DEPRECATED
    MkplUnitStatus.DELETED -> UnitStatus.DELETED
    MkplUnitStatus.NONE -> UnitStatus.NONE
    null -> UnitStatus.NONE
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportUnit() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportUnit() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
