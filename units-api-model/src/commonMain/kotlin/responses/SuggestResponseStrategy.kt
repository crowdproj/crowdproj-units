package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSuggestResponse
import com.crowdproj.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object SuggestResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "suggest"
    override val clazz: KClass<out IUnitResponse> = UnitSuggestResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitSuggestResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitSuggestResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
