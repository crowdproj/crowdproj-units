package com.crowdproj.marketplace.units.common

import com.crowdproj.marketplace.units.common.models.*
import com.crowdproj.marketplace.units.common.stubs.UntsStubs
import kotlinx.datetime.Instant

data class UntsContext(
    var command: UntsCommand = UntsCommand.NONE,
    var state: UntsState = UntsState.NONE,
    val errors: MutableList<UntsError> = mutableListOf(),

    var workMode: UntsWorkMode = UntsWorkMode.PROD,
    var stubCase: UntsStubs = UntsStubs.NONE,

    var requestId: UntsRequestId = UntsRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var unitRequest: UntsUnit = UntsUnit(),
    var unitFilterRequest: UntsUnitFilter = UntsUnitFilter(),
    var unitResponse: UntsUnit = UntsUnit(),
    var unitsResponse: MutableList<UntsUnit> = mutableListOf()
)
