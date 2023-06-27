package com.crowdproj.units.repo.inmemory

import com.crowdproj.units.repo.tests.RepoUnitSuggestTest

class UnitRepoInMemorySuggestTest : RepoUnitSuggestTest() {
    override val repo = UnitRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}