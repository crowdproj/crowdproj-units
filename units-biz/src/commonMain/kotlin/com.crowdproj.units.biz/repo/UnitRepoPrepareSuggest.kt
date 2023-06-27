package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.repoPrepareSuggest(title: String) = worker {
    this.title = title
    description = "Preparing to add suggested unit to DB"
    on { state == MkplState.RUNNING }
    handle {
        unitRepoRead = unitValidated.deepCopy()
        unitRepoPrepare = unitRepoRead
    }
}