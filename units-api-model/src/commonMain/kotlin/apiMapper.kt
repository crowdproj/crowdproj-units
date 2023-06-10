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
        polymorphicDefaultSerializer(IUnitResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is UnitCreateResponse  ->  ResponseSerializer(UnitCreateResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitReadResponse    ->  ResponseSerializer(UnitReadResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitUpdateResponse  ->  ResponseSerializer(UnitUpdateResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitDeleteResponse  ->  ResponseSerializer(UnitDeleteResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitSearchResponse  ->  ResponseSerializer(UnitSearchResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitSuggestResponse ->  ResponseSerializer(UnitSuggestResponse.serializer()) as SerializationStrategy<IUnitResponse>
                is UnitInitResponse    ->  ResponseSerializer(UnitInitResponse.serializer()) as SerializationStrategy<IUnitResponse>
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

fun apiV1ResponseSerialize(Response: IUnitResponse): String = apiMapper.encodeToString(UnitResponseSerializer, Response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV1ResponseDeserialize(json: String): T = apiMapper.decodeFromString(UnitResponseSerializer, json) as T

fun apiV1RequestSerialize(request: IRequest): String = apiMapper.encodeToString(UnitRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV1RequestDeserialize(json: String): T = apiMapper.decodeFromString(UnitRequestSerializer, json) as T
