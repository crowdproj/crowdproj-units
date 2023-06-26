package com.crowdproj.units.common.repo

import com.crowdproj.units.common.models.MkplError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<MkplError>
}