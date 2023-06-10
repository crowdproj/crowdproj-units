package com.crowdproj.units.common

import com.crowdproj.units.logging.common.MpLoggerProvider

data class MkplCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}