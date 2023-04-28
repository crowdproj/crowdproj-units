package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSearchRequest
import com.crowdproj.units.api.v1.models.UnitRequest
import kotlin.reflect.KClass

object SearchRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out UnitRequest> = UnitSearchRequest::class
    override val serializer: KSerializer<out UnitRequest> = UnitSearchRequest.serializer()
    override fun <T : UnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
