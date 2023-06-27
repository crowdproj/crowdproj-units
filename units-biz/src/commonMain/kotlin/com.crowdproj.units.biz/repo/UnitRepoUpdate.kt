package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.repo.DbUnitRequest

fun ICorAddExecDsl<MkplContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        val request = DbUnitRequest(unitRepoPrepare)
        val result = unitRepository.updateUnit(request)
        val resultUnit = result.data
        if (result.isSuccess && resultUnit != null) {
            unitRepoDone = resultUnit
        } else {
            state = MkplState.FAILING
            errors.addAll(result.errors)
            unitRepoDone
        }
    }
}