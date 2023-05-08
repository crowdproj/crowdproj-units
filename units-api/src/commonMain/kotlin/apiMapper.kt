package com.crowdproj.marketplace.units.api.v1

import com.crowdproj.marketplace.units.api.v1.models.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

@OptIn(ExperimentalSerializationApi::class)
val apiMapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IUnitRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateRequest  ->  RequestSerializer(UnitCreateRequest.serializer()) as SerializationStrategy<IUnitRequest>
                is UnitReadRequest    ->  RequestSerializer(UnitReadRequest.serializer()) as SerializationStrategy<IUnitRequest>
                is UnitUpdateRequest  ->  RequestSerializer(UnitUpdateRequest.serializer()) as SerializationStrategy<IUnitRequest>
                is UnitDeleteRequest  ->  RequestSerializer(UnitDeleteRequest.serializer()) as SerializationStrategy<IUnitRequest>
                is UnitSearchRequest  ->  RequestSerializer(UnitSearchRequest.serializer()) as SerializationStrategy<IUnitRequest>
                else -> null
            }
        }
        polymorphicDefault(IUnitRequest::class) {
            UnitRequestSerializer
        }
        polymorphicDefaultSerializer(IUnitResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateResponse  ->  ResponseSerializer(UnitCreateResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitReadResponse    ->  ResponseSerializer(UnitReadResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitUpdateResponse  ->  ResponseSerializer(UnitUpdateResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitDeleteResponse  ->  ResponseSerializer(UnitDeleteResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitSearchResponse  ->  ResponseSerializer(UnitSearchResponse.serializer()) as SerializationStrategy<IUnitResponse>
                else -> null
            }
        }
        polymorphicDefault(IUnitResponse::class) {
            UnitResponseSerializer
        }

        contextual(UnitRequestSerializer)
        contextual(UnitResponseSerializer)
    }
}

fun Json.encodeResponse(response: IUnitResponse): String = encodeToString(UnitResponseSerializer, response)
