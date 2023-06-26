package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Preparing data to save to DB: combining DB data with " +
            "user-provided data"
    on { state == MkplState.RUNNING }
    handle {
        unitRepoPrepare = unitRepoRead.deepCopy().apply {
            this.name = unitValidated.name
            description = unitValidated.description
            alias = unitValidated.alias
            status = unitValidated.status
        }
    }
}