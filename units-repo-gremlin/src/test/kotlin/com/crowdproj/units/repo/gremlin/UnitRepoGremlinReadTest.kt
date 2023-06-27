package com.crowdproj.units.repo.gremlin

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.repo.tests.RepoUnitReadTest

class UnitRepoGremlinReadTest : RepoUnitReadTest() {
    override val repo: UnitRepoGremlin by lazy {
        UnitRepoGremlin(
            properties = GremlinProperties(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
            ),
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
        )
    }
    override val readSucc: MkplUnit by lazy { repo.initializedObjects[0] }
}