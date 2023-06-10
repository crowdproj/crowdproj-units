package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitUpdateRequest
import com.crowdproj.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IUnitRequest> = UnitUpdateRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
