package com.crowdproj.marketplace.units.common.models

data class UntsError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
