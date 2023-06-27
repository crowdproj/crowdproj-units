package com.crowdproj.units.repo.gremlin

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.repo.tests.RepoUnitDeleteTest

class UnitRepoGremlinDeleteTest : RepoUnitDeleteTest() {
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
    override val deleteSucc: MkplUnit by lazy { repo.initializedObjects[0] }
    override val deleteConc: MkplUnit by lazy { repo.initializedObjects[1] }
    override val notFoundId: MkplUnitId = MkplUnitId("#3100:0")
}