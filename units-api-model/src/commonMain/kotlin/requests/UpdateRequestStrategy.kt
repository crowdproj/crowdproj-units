package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitUpdateRequest
import com.crowdproj.units.api.v1.models.UnitRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out UnitRequest> = UnitUpdateRequest::class
    override val serializer: KSerializer<out UnitRequest> = UnitUpdateRequest.serializer()
    override fun <T : UnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
