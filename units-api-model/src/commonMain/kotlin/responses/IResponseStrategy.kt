package com.crowdproj.units.api.v1.responses

import com.crowdproj.units.api.v1.IApiStrategy
import com.crowdproj.units.api.v1.models.IResponse

sealed interface IResponseStrategy: IApiStrategy<IResponse> {
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
