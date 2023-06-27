package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitUpdateTest

class UnitRepoInMemoryUpdateTest : RepoUnitUpdateTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}