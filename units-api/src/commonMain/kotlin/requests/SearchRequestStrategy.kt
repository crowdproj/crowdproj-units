package com.crowdproj.marketplace.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitSearchRequest
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object SearchRequestStrategy: IUnitRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IUnitRequest> = UnitSearchRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitSearchRequest.serializer()
    override fun <T : IUnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
