package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSuggestResponse
import com.crowdproj.units.api.v1.models.IResponse
import kotlin.reflect.KClass

object SuggestResponseStrategy: IResponseStrategy {
    override val discriminator: String = "suggest"
    override val clazz: KClass<out IResponse> = UnitSuggestResponse::class
    override val serializer: KSerializer<out IResponse> = UnitSuggestResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is UnitSuggestResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
