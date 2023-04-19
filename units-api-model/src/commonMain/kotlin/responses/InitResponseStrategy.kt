package com.crowdproj.units.api.v1.responses

import com.crowdproj.units.api.v1.models.IResponse
import com.crowdproj.units.api.v1.models.UnitInitResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object InitResponseStrategy: IResponseStrategy {
    override val discriminator: String = "init"
    override val clazz: KClass<out IResponse> = UnitInitResponse::class
    override val serializer: KSerializer<out IResponse> = UnitInitResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is UnitInitResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
