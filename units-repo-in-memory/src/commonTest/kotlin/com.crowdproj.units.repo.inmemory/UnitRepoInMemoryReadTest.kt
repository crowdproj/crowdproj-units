package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitReadTest

class UnitRepoInMemoryReadTest : RepoUnitReadTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects
    )
}