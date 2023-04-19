package com.crowdproj.units.common

import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
import kotlinx.datetime.Instant

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var unitRequest: MkplUnit = MkplUnit(),
    var unitFilterRequest: MkplUnitFilter = MkplUnitFilter(),
    var unitResponse: MkplUnit = MkplUnit(),
    var unitsResponse: MutableList<MkplUnit> = mutableListOf()
)
