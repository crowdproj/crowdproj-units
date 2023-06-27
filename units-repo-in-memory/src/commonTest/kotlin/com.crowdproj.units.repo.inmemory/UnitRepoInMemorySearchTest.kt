package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitSearchTest

class UnitRepoInMemorySearchTest : RepoUnitSearchTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects
    )
}