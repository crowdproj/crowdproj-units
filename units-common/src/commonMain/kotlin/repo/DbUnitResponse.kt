package com.crowdproj.units.common.repo

import com.crowdproj.units.common.helpers.errorRepoConcurrency
import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.helpers.errorEmptyId as mkplErrorEmptyId
import com.crowdproj.units.common.helpers.errorNotFound as mkplErrorNotFound

data class DbUnitResponse(
    override val data: MkplUnit?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList(),
) : IDbResponse<MkplUnit> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbUnitResponse(null, true)
        fun success(result: MkplUnit) = DbUnitResponse(result, true)
        fun error(errors: List<MkplError>, data: MkplUnit? = null) = DbUnitResponse(data, false, errors)
        fun error(error: MkplError, data: MkplUnit? = null) = DbUnitResponse(data, false, listOf(error))

        val errorEmptyId = error(mkplErrorEmptyId)

        fun errorConcurrent(lock: MkplUnitLock, unit: MkplUnit?) = error(
            errorRepoConcurrency(lock, unit?.lock?.let { MkplUnitLock(it.asString()) }),
            unit
        )

        val errorNotFound = error(mkplErrorNotFound)

    }
}
