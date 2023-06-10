package com.crowdproj.units.springapp.config

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.logging.common.MpLoggerProvider
import com.crowdproj.units.logging.jvm.mpLoggerLogback
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CorConfig {
    @Bean
    fun loggerProvider(): MpLoggerProvider = MpLoggerProvider { mpLoggerLogback(it) }

    @Bean
    fun corSettings(): MkplCorSettings = MkplCorSettings(
        loggerProvider = loggerProvider()
    )

    @Bean
    fun mkplUnitProcessor(corSettings: MkplCorSettings) = MkplUnitProcessor()

}