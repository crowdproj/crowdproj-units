package com.crowdproj.units.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSuggestRequest
import com.crowdproj.units.api.v1.models.IRequest
import kotlin.reflect.KClass

object SuggestRequestStrategy: IRequestStrategy {
    override val discriminator: String = "suggest"
    override val clazz: KClass<out IRequest> = UnitSuggestRequest::class
    override val serializer: KSerializer<out IRequest> = UnitSuggestRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is UnitSuggestRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
