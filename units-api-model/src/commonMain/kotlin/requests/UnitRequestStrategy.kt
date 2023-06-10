package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.UnitApiStrategy
import com.crowdproj.units.api.v1.models.IRequest

sealed interface UnitRequestStrategy: UnitApiStrategy<IRequest> {
    companion object {
        private val members: List<UnitRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            SearchRequestStrategy,
            SuggestRequestStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
