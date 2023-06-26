package com.crowdproj.units.common

import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.IUnitRepository
import com.crowdproj.units.common.stubs.MkplStubs
import kotlinx.datetime.Instant

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),
    var settings: MkplCorSettings = MkplCorSettings.NONE,

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var unitRepository: IUnitRepository = IUnitRepository.NONE,
    var unitRepoRead: MkplUnit = MkplUnit(),
    var unitRepoPrepare: MkplUnit = MkplUnit(),
    var unitRepoDone: MkplUnit = MkplUnit(),
    var unitsRepoDone: MutableList<MkplUnit> = mutableListOf(),

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var unitRequest: MkplUnit = MkplUnit(),
    var unitFilterRequest: MkplUnitFilter = MkplUnitFilter(),

    var unitValidating: MkplUnit = MkplUnit(),
    var unitFilterValidating: MkplUnitFilter = MkplUnitFilter(),

    var unitValidated: MkplUnit = MkplUnit(),
    var unitFilterValidated: MkplUnitFilter = MkplUnitFilter(),

    var unitResponse: MkplUnit = MkplUnit(),
    var unitsResponse: MutableList<MkplUnit> = mutableListOf()
)
