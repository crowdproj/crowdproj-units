package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.models.MkplUnitStatus

abstract class BaseInitUnits(val op: String): IInitObjects<MkplUnit> {

open val lockOld: MkplUnitLock = MkplUnitLock("20000000-0000-0000-0000-000000000001")
open val lockBad: MkplUnitLock = MkplUnitLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        name: String = "",
        status: MkplUnitStatus = MkplUnitStatus.CONFIRMED,
        lock: MkplUnitLock = lockOld,
    ) = MkplUnit(
        id = MkplUnitId("unit-repo-$op-$suf"),
        name = name,
        description = "$suf stub description",
        status = status,
        lock = lock,
    )
}