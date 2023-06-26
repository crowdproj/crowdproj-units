package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitDeleteTest

class UnitRepoInMemoryDeleteTest : RepoUnitDeleteTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects
    )
}