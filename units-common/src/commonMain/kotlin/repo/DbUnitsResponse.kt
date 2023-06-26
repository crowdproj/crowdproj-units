package com.crowdproj.units.common.repo

import com.crowdproj.units.common.models.MkplError
import com.crowdproj.units.common.models.MkplUnit

data class DbUnitsResponse(
    override val data: List<MkplUnit>?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList(),
): IDbResponse<List<MkplUnit>> {
    companion object {
        val MOCK_SUCCESS_EMPTY = DbUnitsResponse(emptyList(), true)
        fun success(result: List <MkplUnit>) = DbUnitsResponse(result, true)
        fun error(errors: List<MkplError>) = DbUnitsResponse(null, false, errors)
        fun error(error: MkplError) = DbUnitsResponse(null, false, listOf(error))
    }
}
