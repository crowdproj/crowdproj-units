package com.crowdproj.units.repo.gremlin.mappers

import com.crowdproj.units.common.models.MkplUnit

fun MkplUnit.label(): String? = this::class.simpleName