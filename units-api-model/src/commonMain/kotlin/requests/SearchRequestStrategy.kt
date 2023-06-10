package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSearchRequest
import com.crowdproj.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object SearchRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IUnitRequest> = UnitSearchRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitSearchRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
