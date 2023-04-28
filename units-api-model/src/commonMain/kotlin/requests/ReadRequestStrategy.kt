package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitReadRequest
import com.crowdproj.units.api.v1.models.UnitRequest
import kotlin.reflect.KClass

object ReadRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out UnitRequest> = UnitReadRequest::class
    override val serializer: KSerializer<out UnitRequest> = UnitReadRequest.serializer()
    override fun <T : UnitRequest> fillDiscriminator(req: T): T {
        require(req is UnitReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
