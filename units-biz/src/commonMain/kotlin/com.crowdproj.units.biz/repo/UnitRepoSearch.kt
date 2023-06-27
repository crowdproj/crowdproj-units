package com.crowdproj.units.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.repo.DbUnitFilterRequest

fun ICorAddExecDsl<MkplContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Searching unit(s) by filter"
    on { state == MkplState.RUNNING }
    handle {
        val request = DbUnitFilterRequest(
            nameFilter = unitFilterValidated.searchString,
            unitId = unitFilterValidated.unitId,
            unitStatus = unitFilterValidated.unitStatus,
        )
        val result = unitRepository.searchUnit(request)
        val resultUnits = result.data
        if (result.isSuccess && resultUnits != null) {
            unitsRepoDone = resultUnits.toMutableList()
        } else {
            state = MkplState.FAILING
            errors.addAll(result.errors)
        }
    }
}