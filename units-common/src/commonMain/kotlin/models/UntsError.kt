package com.crowdproj.marketplace.units.common.models

data class UntsError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val title: String = "",
    val description: String = "",
    val exception: Throwable? = null,
)
