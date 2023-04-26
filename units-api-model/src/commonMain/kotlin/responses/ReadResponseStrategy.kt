package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitReadResponse
import com.crowdproj.units.api.v1.models.UnitResponse
import kotlin.reflect.KClass

object ReadResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out UnitResponse> = UnitReadResponse::class
    override val serializer: KSerializer<out UnitResponse> = UnitReadResponse.serializer()
    override fun <T : UnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
