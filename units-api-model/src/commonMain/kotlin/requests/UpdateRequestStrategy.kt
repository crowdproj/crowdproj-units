package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitUpdateRequest
import com.crowdproj.units.api.v1.models.IRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IRequest> = UnitUpdateRequest::class
    override val serializer: KSerializer<out IRequest> = UnitUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
