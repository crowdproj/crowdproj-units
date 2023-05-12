package com.crowdproj.marketplace.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitCreateRequest
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object CreateRequestStrategy: IUnitRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IUnitRequest> = UnitCreateRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitCreateRequest.serializer()
    override fun <T : IUnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
