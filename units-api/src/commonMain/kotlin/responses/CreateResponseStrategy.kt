package com.crowdproj.marketplace.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitCreateResponse
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object CreateResponseStrategy: IUnitResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IUnitResponse> = UnitCreateResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitCreateResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
