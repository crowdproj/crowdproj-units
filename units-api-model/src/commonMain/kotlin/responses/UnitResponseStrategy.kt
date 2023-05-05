package com.crowdproj.units.api.v1.responses

import com.crowdproj.units.api.v1.UnitApiStrategy
import com.crowdproj.units.api.v1.models.IUnitResponse

sealed interface UnitResponseStrategy: UnitApiStrategy<IUnitResponse> {
    companion object {
        val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            SearchResponseStrategy,
            SuggestResponseStrategy,
            InitResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
