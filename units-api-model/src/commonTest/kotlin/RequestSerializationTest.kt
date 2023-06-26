package com.crowdproj.units.api.v1

import com.crowdproj.units.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json

class RequestSerializationTest {
    private val createRequest = UnitCreateRequest(
        requestId = "1",
        debug = UnitDebug(
            mode = UnitRequestDebugMode.STUB,
            stub = UnitRequestDebugStubs.BAD_ID
        ),
        unit = UnitCreateObject(
            name = "kg",
            description = "unit of mass in the International System of Units",
            alias = "kilogram / kilogramme"
        )
    )

    private val suggestRequest = UnitSuggestRequest(
        requestId = "2",
        debug = UnitDebug(
            mode = UnitRequestDebugMode.STUB,
            stub = UnitRequestDebugStubs.SUCCESS
        ),
        unit = UnitSuggestObject(
            name = "ft",
            description = "unit of length in the British imperial and United States customary systems of measurement",
            alias = "foot"
        )
    )

    @Test
    fun serializeCreateRequest() {
        val json = Json.encodeToString(IUnitRequest.serializer(), createRequest)

        assertContains(json, Regex("\"name\":\\s*\"kg\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badId\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserializeCreateRequest() {
        val json = Json.encodeToString(IUnitRequest.serializer(), createRequest)
        val obj = Json.decodeFromString(IUnitRequest.serializer(), json) as UnitCreateRequest

        assertEquals(createRequest, obj)
    }

    @Test
    fun serializeSuggestRequest() {
        val json = Json.encodeToString(IUnitRequest.serializer(), suggestRequest)

        assertContains(json, Regex("\"requestType\":\\s*\"suggest\""))
    }
}