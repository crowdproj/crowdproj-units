package com.crowdproj.units.api.v1.requests

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSuggestRequest
import com.crowdproj.units.api.v1.models.IUnitRequest
import kotlin.reflect.KClass

object SuggestRequestStrategy: UnitRequestStrategy {
    override val discriminator: String = "suggest"
    override val clazz: KClass<out IUnitRequest> = UnitSuggestRequest::class
    override val serializer: KSerializer<out IUnitRequest> = UnitSuggestRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitSuggestRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
