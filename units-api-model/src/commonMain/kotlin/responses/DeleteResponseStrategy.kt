package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitDeleteResponse
import com.crowdproj.units.api.v1.models.UnitResponse
import kotlin.reflect.KClass

object DeleteResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out UnitResponse> = UnitDeleteResponse::class
    override val serializer: KSerializer<out UnitResponse> = UnitDeleteResponse.serializer()
    override fun <T : UnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
