package com.crowdproj.marketplace.units.api.v1.requests

import com.crowdproj.marketplace.units.api.v1.IApiStrategy
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest

sealed interface IUnitRequestStrategy: IApiStrategy<IUnitRequest> {
    companion object {
        private val members: List<IUnitRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            SearchRequestStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
