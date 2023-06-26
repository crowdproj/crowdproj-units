package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitCreateTest

class UnitRepoInMemoryCreateTest : RepoUnitCreateTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}