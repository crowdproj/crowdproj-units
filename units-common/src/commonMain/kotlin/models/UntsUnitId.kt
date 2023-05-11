package com.crowdproj.marketplace.units.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class UntsUnitId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = UntsUnitId("")
    }
}
