package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState

fun ICorAddExecDsl<MkplContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Preparing data to delete from DB
    """.trimIndent()
    on { state == MkplState.RUNNING }
    handle {
        unitRepoPrepare = unitValidated.deepCopy()
    }
}