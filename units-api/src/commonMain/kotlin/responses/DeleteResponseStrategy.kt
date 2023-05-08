package com.crowdproj.marketplace.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitDeleteResponse
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object DeleteResponseStrategy: IUnitResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IUnitResponse> = UnitDeleteResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitDeleteResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
