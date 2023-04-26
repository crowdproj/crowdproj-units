package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitUpdateResponse
import com.crowdproj.units.api.v1.models.UnitResponse
import kotlin.reflect.KClass

object UpdateResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out UnitResponse> = UnitUpdateResponse::class
    override val serializer: KSerializer<out UnitResponse> = UnitUpdateResponse.serializer()
    override fun <T : UnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
