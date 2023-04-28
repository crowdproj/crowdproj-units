package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.UnitApiStrategy
import com.crowdproj.units.api.v1.models.IUnitRequest

sealed interface UnitRequestStrategy: UnitApiStrategy<IUnitRequest> {
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
