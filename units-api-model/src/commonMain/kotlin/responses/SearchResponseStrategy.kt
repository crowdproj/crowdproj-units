package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitSearchResponse
import com.crowdproj.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object SearchResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IUnitResponse> = UnitSearchResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitSearchResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
