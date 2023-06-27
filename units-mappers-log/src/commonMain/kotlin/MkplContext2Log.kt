import com.crowdproj.units.api.logs.models.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import kotlinx.datetime.Clock

fun MkplContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "crowdproj-unit",
    unit = toUnitLog(),
    errors = errors.map { it.toLog() },
)

fun MkplContext.toUnitLog(): UnitLogModel? {
    val unitNone = MkplUnit()
    return UnitLogModel(
        requestId = requestId.takeIf { it != MkplRequestId.NONE }?.asString(),
        requestUnit = unitRequest.takeIf { it != unitNone }?.toLog(),
        responseUnit = unitResponse.takeIf { it != unitNone }?.toLog(),
        responseUnits = unitsResponse.takeIf { it.isNotEmpty() }?.filter { it != unitNone }?.map { it.toLog() },
        requestFilter = unitFilterRequest.takeIf { it != MkplUnitFilter() }?.toLog(),
    ).takeIf { it != UnitLogModel() }
}

private fun MkplUnitFilter.toLog() = UnitFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
)

fun MkplError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun MkplUnit.toLog() = UnitLog(
    id = id.takeIf { it != MkplUnitId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    alias = alias.takeIf { it.isNotBlank() },
    status = status.takeIf { it != MkplUnitStatus.NONE }?.name,
    systemUnitId = systemUnitId.takeIf { it != MkplSystemUnitId.NONE }?.asString(),
)
