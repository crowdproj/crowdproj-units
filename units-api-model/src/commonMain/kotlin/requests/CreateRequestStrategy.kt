package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitCreateRequest
import com.crowdproj.units.api.v1.models.UnitRequest
import kotlin.reflect.KClass

object CreateRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out UnitRequest> = UnitCreateRequest::class
    override val serializer: KSerializer<out UnitRequest> = UnitCreateRequest.serializer()
    override fun <T : UnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
