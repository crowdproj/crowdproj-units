package com.crowdproj.units.common.models

data class MkplUnitFilter(
    var searchString: String = "",
    var unitId: MkplUnitId = MkplUnitId.NONE,
    var unitStatus: MkplUnitStatus = MkplUnitStatus.NONE,
)
