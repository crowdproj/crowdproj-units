package com.crowdproj.marketplace.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitDeleteRequest
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object DeleteRequestStrategy: IUnitRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IUnitRequest> = UnitDeleteRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitDeleteRequest.serializer()
    override fun <T : IUnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
