package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitDeleteRequest
import com.crowdproj.units.api.v1.models.IRequest
import kotlin.reflect.KClass

object DeleteRequestStrategy: IRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IRequest> = UnitDeleteRequest::class
    override val serializer: KSerializer<out IRequest> = UnitDeleteRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
