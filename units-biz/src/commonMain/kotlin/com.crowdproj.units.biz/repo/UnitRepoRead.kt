package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.repo.DbUnitIdRequest

fun ICorAddExecDsl<MkplContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Reading unit from DB"
    on { state == MkplState.RUNNING }
    handle {
        val request = DbUnitIdRequest(unitValidated)
        val result = unitRepository.readUnit(request)
        val resultUnit = result.data
        if (result.isSuccess && resultUnit != null) {
            unitRepoRead = resultUnit
        } else {
            state = MkplState.FAILING
            errors.addAll(result.errors)
        }
    }
}