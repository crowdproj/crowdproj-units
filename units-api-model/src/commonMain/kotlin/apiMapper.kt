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
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateRequest  ->  RequestSerializer(UnitCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is UnitReadRequest    ->  RequestSerializer(UnitReadRequest.serializer()) as SerializationStrategy<IRequest>
                is UnitUpdateRequest  ->  RequestSerializer(UnitUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is UnitDeleteRequest  ->  RequestSerializer(UnitDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is UnitSearchRequest  ->  RequestSerializer(UnitSearchRequest.serializer()) as SerializationStrategy<IRequest>
                is UnitSuggestRequest ->  RequestSerializer(UnitSuggestRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            UnitRequestSerializer
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateResponse  ->  ResponseSerializer(UnitCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitReadResponse    ->  ResponseSerializer(UnitReadResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitUpdateResponse  ->  ResponseSerializer(UnitUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitDeleteResponse  ->  ResponseSerializer(UnitDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitSearchResponse  ->  ResponseSerializer(UnitSearchResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitSuggestResponse ->  ResponseSerializer(UnitSuggestResponse.serializer()) as SerializationStrategy<IResponse>
                is UnitInitResponse    ->  ResponseSerializer(UnitInitResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponse::class) {
            UnitResponseSerializer
        }

        contextual(UnitRequestSerializer)
        contextual(UnitResponseSerializer)
    }
}

fun Json.encodeResponse(response: IResponse): String = encodeToString(UnitResponseSerializer, response)