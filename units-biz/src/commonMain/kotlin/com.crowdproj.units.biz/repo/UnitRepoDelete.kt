package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.repo.DbUnitIdRequest

fun ICorAddExecDsl<MkplContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Deleting unit from DB by ID"
    on { state == MkplState.RUNNING }
    handle {
        val request = DbUnitIdRequest(unitRepoPrepare)
        val result = unitRepository.deleteUnit(request)
        if (!result.isSuccess) {
            state = MkplState.FAILING
            errors.addAll(result.errors)
        }
        unitRepoDone = unitRepoRead
    }
}