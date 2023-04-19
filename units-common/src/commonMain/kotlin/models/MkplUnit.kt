package com.crowdproj.units.common.models

data class MkplUnit(
    var id: MkplUnitId = MkplUnitId.NONE,
    var name: String = "",
    var alias: String = "",
    var description: String = "",
    var status: MkplUnitStatus = MkplUnitStatus.NONE,
    var systemUnitId: MkplSystemUnitId = MkplSystemUnitId.NONE,
)
