package com.crowdproj.marketplace.units.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class UntsRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = UntsRequestId("")
    }
}
