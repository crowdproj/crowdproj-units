package com.crowdproj.units.common

import com.crowdproj.units.common.repo.IUnitRepository
import com.crowdproj.units.logging.common.MpLoggerProvider

data class MkplCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val repoStub: IUnitRepository = IUnitRepository.NONE,
    val repoTest: IUnitRepository = IUnitRepository.NONE,
    val repoProd: IUnitRepository = IUnitRepository.NONE,
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}