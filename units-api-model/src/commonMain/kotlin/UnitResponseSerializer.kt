package com.crowdproj.units.api.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.units.api.v1.models.UnitResponse
import com.crowdproj.units.api.v1.responses.UnitResponseStrategy


val UnitResponseSerializer = ResponseSerializer(UnitResponseSerializerBase)

private object UnitResponseSerializerBase : JsonContentPolymorphicSerializer<UnitResponse>(UnitResponse::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out UnitResponse> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return UnitResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${UnitResponse::class} implementation"
            )
    }
}

class ResponseSerializer<T : UnitResponse>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        UnitResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as UnitResponse instance in ResponseSerializer"
            )
}
