package com.crowdproj.marketplace.units.api.v1.responses

import com.crowdproj.marketplace.units.api.v1.IApiStrategy
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse

sealed interface IUnitResponseStrategy: IApiStrategy<IUnitResponse> {
    companion object {
        val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            SearchResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
