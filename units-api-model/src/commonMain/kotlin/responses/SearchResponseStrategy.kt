package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSearchResponse
import com.crowdproj.units.api.v1.models.UnitResponse
import kotlin.reflect.KClass

object SearchResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out UnitResponse> = UnitSearchResponse::class
    override val serializer: KSerializer<out UnitResponse> = UnitSearchResponse.serializer()
    override fun <T : UnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
