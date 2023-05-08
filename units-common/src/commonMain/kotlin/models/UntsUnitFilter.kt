package com.crowdproj.marketplace.units.common.models

data class UntsUnitFilter(
    var searchString: String = "",
    var ownerId: UntsUnitId = UntsUnitId.NONE,
    var unitStatus: UntsUnitStatus = UntsUnitStatus.NONE,
)
