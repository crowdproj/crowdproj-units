package com.crowdproj.units.common.repo

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitLock

data class DbUnitIdRequest(
    val id: MkplUnitId,
    val lock: MkplUnitLock = MkplUnitLock.NONE
) {
    constructor(unit: MkplUnit): this(unit.id, unit.lock)
}

