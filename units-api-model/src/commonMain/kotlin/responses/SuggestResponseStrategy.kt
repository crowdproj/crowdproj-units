package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSuggestResponse
import com.crowdproj.units.api.v1.models.UnitResponse
import kotlin.reflect.KClass

object SuggestResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "suggest"
    override val clazz: KClass<out UnitResponse> = UnitSuggestResponse::class
    override val serializer: KSerializer<out UnitResponse> = UnitSuggestResponse.serializer()
    override fun <T : UnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitSuggestResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
