package com.crowdproj.units.repo.inmemory.model

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.models.MkplUnitStatus


class UnitEntity(
    val id: String? = null,
    val name: String? = null,
    val alias: String? = null,
    val description: String? = null,
    val status: String? = null,
    val lock: String? = null,
) {
    constructor(model: MkplUnit): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        alias = model.alias.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        status = model.status.takeIf { it != MkplUnitStatus.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() },
    )

    fun toInternal() = MkplUnit(
        id = id?.let { MkplUnitId(it) }?: MkplUnitId.NONE,
        name = name?: "",
        alias = alias?: "",
        description = description?: "",
        status = status?.let { MkplUnitStatus.valueOf(it) }?: MkplUnitStatus.NONE,
        lock = lock?.let { MkplUnitLock(it) }?: MkplUnitLock.NONE
    )
}