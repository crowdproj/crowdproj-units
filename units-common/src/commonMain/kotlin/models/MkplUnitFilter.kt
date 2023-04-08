package com.crowdproj.units.common.models

data class MkplUnitFilter(
    var searchString: String = "",
    var ownerId: MkplUnitId = MkplUnitId.NONE,
    var unitStatus: MkplUnitStatus = MkplUnitStatus.NONE,
)
