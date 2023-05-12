package com.crowdproj.marketplace.units.api.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.marketplace.units.api.v1.models.IUnitRequest
import com.crowdproj.marketplace.units.api.v1.requests.IUnitRequestStrategy


val UnitRequestSerializer = RequestSerializer(UnitRequestSerializerBase)

private object UnitRequestSerializerBase : JsonContentPolymorphicSerializer<IUnitRequest>(IUnitRequest::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IUnitRequest> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IUnitRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IUnitRequest::class} implementation"
            )
    }
}

class RequestSerializer<T : IUnitRequest>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IUnitRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IUnitRequest instance in RequestSerializer"
            )
}
