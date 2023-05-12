package com.crowdproj.marketplace.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitUpdateRequest
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy: IUnitRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IUnitRequest> = UnitUpdateRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitUpdateRequest.serializer()
    override fun <T : IUnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
