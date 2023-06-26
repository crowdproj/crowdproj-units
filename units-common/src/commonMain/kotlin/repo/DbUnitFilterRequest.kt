package com.crowdproj.units.common.repo

import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitStatus

data class DbUnitFilterRequest(
    val nameFilter: String = "",
    val unitId: MkplUnitId = MkplUnitId.NONE,
    val unitStatus: MkplUnitStatus = MkplUnitStatus.NONE,
)
