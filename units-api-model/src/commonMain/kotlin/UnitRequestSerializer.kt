package com.crowdproj.units.api.v1

import com.crowdproj.units.api.v1.models.IRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.units.api.v1.requests.UnitRequestStrategy


val UnitRequestSerializer = RequestSerializer(UnitRequestSerializerBase)

private object UnitRequestSerializerBase : JsonContentPolymorphicSerializer<IRequest>(IRequest::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequest> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return UnitRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IRequest::class} implementation"
            )
    }
}

class RequestSerializer<T : IRequest>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        UnitRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IUnitRequest instance in RequestSerializer"
            )
}
