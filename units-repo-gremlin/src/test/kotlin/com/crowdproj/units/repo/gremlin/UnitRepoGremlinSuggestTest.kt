package com.crowdproj.units.repo.gremlin

import com.crowdproj.units.common.repo.IUnitRepository
import com.crowdproj.units.repo.tests.RepoUnitSearchTest
import com.crowdproj.units.repo.tests.RepoUnitSuggestTest

class UnitRepoGremlinSuggestTest : RepoUnitSuggestTest() {
    override val repo: IUnitRepository by lazy {
        UnitRepoGremlin(
            properties = GremlinProperties(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
            ),
            initObjects = RepoUnitSearchTest.initObjects,
            initRepo = { g -> g.V().drop().iterate() },
            randomUuid = { lockNew.asString() }
        )
    }
}