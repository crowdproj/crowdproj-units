package com.crowdproj.marketplace.units.api.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse
import com.crowdproj.marketplace.units.api.v1.responses.IUnitResponseStrategy


val UnitResponseSerializer = ResponseSerializer(UnitResponseSerializerBase)

private object UnitResponseSerializerBase : JsonContentPolymorphicSerializer<IUnitResponse>(IUnitResponse::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IUnitResponse> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IUnitResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IUnitResponse::class} implementation"
            )
    }
}

class ResponseSerializer<T : IUnitResponse>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IUnitResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IUnitResponse instance in ResponseSerializer"
            )
}
