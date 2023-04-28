package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitDeleteRequest
import com.crowdproj.units.api.v1.models.UnitRequest
import kotlin.reflect.KClass

object DeleteRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out UnitRequest> = UnitDeleteRequest::class
    override val serializer: KSerializer<out UnitRequest> = UnitDeleteRequest.serializer()
    override fun <T : UnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
