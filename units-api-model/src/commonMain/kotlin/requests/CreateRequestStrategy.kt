package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitCreateRequest
import com.crowdproj.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object CreateRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IUnitRequest> = UnitCreateRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
