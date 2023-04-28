package com.crowdproj.units.api.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import com.crowdproj.units.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiMapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(UnitRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateRequest  ->  RequestSerializer(UnitCreateRequest.serializer()) as SerializationStrategy<UnitRequest>
                is UnitReadRequest    ->  RequestSerializer(UnitReadRequest.serializer()) as SerializationStrategy<UnitRequest>
                is UnitUpdateRequest  ->  RequestSerializer(UnitUpdateRequest.serializer()) as SerializationStrategy<UnitRequest>
                is UnitDeleteRequest  ->  RequestSerializer(UnitDeleteRequest.serializer()) as SerializationStrategy<UnitRequest>
                is UnitSearchRequest  ->  RequestSerializer(UnitSearchRequest.serializer()) as SerializationStrategy<UnitRequest>
                is UnitSuggestRequest ->  RequestSerializer(UnitSuggestRequest.serializer()) as SerializationStrategy<UnitRequest>
                else -> null
            }
        }
        polymorphicDefault(UnitRequest::class) {
            UnitRequestSerializer
        }
        polymorphicDefaultSerializer(UnitResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateResponse  ->  ResponseSerializer(UnitCreateResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitReadResponse    ->  ResponseSerializer(UnitReadResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitUpdateResponse  ->  ResponseSerializer(UnitUpdateResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitDeleteResponse  ->  ResponseSerializer(UnitDeleteResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitSearchResponse  ->  ResponseSerializer(UnitSearchResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitSuggestResponse ->  ResponseSerializer(UnitSuggestResponse.serializer()) as SerializationStrategy<UnitResponse>
                is UnitInitResponse    ->  ResponseSerializer(UnitInitResponse.serializer()) as SerializationStrategy<UnitResponse>
                else -> null
            }
        }
        polymorphicDefault(UnitResponse::class) {
            UnitResponseSerializer
        }

        contextual(UnitRequestSerializer)
        contextual(UnitResponseSerializer)
    }
}

fun Json.encodeResponse(response: UnitResponse): String = encodeToString(UnitResponseSerializer, response)