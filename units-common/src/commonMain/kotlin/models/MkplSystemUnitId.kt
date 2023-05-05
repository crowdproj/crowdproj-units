package com.crowdproj.units.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkplSystemUnitId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplSystemUnitId("")
    }
}