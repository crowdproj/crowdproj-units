package com.crowdproj.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.units.api.v1.models.UnitReadResponse
import com.crowdproj.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object ReadResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IUnitResponse> = UnitReadResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitReadResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
