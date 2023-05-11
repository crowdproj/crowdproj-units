package com.crowdproj.marketplace.units.common.models

data class UntsUnit(
    var id: UntsUnitId = UntsUnitId.NONE,
    var unit: String = "",
    var alias: String = "",
    var description: String = "",
    var status: UntsUnitStatus = UntsUnitStatus.NONE,
    var systemUnitId: UntsUnitId = UntsUnitId.NONE,
    var conversion: UntsConversionParameters = UntsConversionParameters.NONE
)
