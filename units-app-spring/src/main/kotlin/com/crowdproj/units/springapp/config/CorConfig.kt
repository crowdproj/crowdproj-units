package com.crowdproj.units.springapp.config

import com.crowdproj.units.repo.gremlin.GremlinProperties
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.repo.IUnitRepository
import com.crowdproj.units.logging.common.MpLoggerProvider
import com.crowdproj.units.logging.jvm.mpLoggerLogback
import com.crowdproj.units.repo.gremlin.UnitRepoGremlin
import com.crowdproj.units.repo.inmemory.UnitRepoInMemory
import com.crowdproj.units.repo.stubs.UnitRepoStub
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GremlinPropertiesEx::class)
class CorConfig {
    @Bean
    fun loggerProvider(): MpLoggerProvider = MpLoggerProvider { mpLoggerLogback(it) }

    @Bean
    fun prodRepository(gremlinProperties: GremlinProperties) = UnitRepoGremlin(properties = gremlinProperties)

    @Bean
    fun testRepository() = UnitRepoInMemory()

    @Bean
    fun stubRepository() = UnitRepoStub()

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IUnitRepository,
        @Qualifier("testRepository") testRepository: IUnitRepository,
        @Qualifier("stubRepository") stubRepository: IUnitRepository,
    ): MkplCorSettings = MkplCorSettings(
        loggerProvider = loggerProvider(),
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository,
    )

    @Bean
    fun mkplUnitProcessor(corSettings: MkplCorSettings) = MkplUnitProcessor(settings = corSettings)

}