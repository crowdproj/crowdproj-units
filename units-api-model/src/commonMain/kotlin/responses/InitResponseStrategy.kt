package com.crowdproj.units.api.v1.responses

import com.crowdproj.units.api.v1.models.IUnitResponse
import com.crowdproj.units.api.v1.models.UnitInitResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object InitResponseStrategy: UnitResponseStrategy {
    override val discriminator: String = "init"
    override val clazz: KClass<out IUnitResponse> = UnitInitResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitInitResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitInitResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
