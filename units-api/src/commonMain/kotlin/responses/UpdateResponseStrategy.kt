package com.crowdproj.marketplace.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.UnitUpdateResponse
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse
import kotlin.reflect.KClass

object UpdateResponseStrategy: IUnitResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IUnitResponse> = UnitUpdateResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitUpdateResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
