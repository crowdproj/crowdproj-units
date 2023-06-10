package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitReadRequest
import com.crowdproj.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object ReadRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IUnitRequest> = UnitReadRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
