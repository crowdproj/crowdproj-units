package com.crowdproj.marketplace.units.api.v1

import com.crowdproj.marketplace.units.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class RequestSerializationTest {
    private val createRequest = UnitCreateRequest(
        requestType = "create",
        requestId = "1",
        debug = UnitDebug(
            mode = UnitRequestDebugMode.STUB,
            stub = UnitRequestDebugStubs.BAD_ID
        ),
        params = UnitCreateParams(
            unit = "kg",
            description = "unit of mass in the International System of Units",
            alias = "kilogram / kilogramme",
            conversion = UnitConversionParameters(),
            status = UnitStatus.CONFIRMED
        )
    )

    private val searchRequest = UnitSearchRequest(
        requestType = "search",
        requestId = "2",
        debug = UnitDebug(
            mode = UnitRequestDebugMode.STUB,
            stub = UnitRequestDebugStubs.SUCCESS
        ),
        params = UnitSearchParams(
            unitFilter = UnitSearchFilter(
                search = "mass"
            )
        ),
    )

    @Test
    fun serializeCreateRequest() {
        val json = apiMapper.encodeToString(createRequest)

        assertContains(json, Regex("\"unit\":\\s*\"kg\""))
        assertContains(json, Regex("\"alias\":\\s*\"kilogram / kilogramme\""))
        assertContains(json, Regex("\"description\":\\s*\"unit of mass in the International System of Units\""))
        assertContains(json, Regex("\"status\":\\s*\"${UnitStatus.CONFIRMED}\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"conversion\":\\s*\\{\"A\":1.0,\"B\":0.0,\"C\":1.0,\"D\":0.0}"))
        assertContains(json, Regex("\"stub\":\\s*\"badId\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserializeCreateRequest() {
        val json = apiMapper.encodeToString(createRequest)
        val obj = apiMapper.decodeFromString(json) as UnitCreateRequest

        assertEquals(createRequest, obj)
    }

    @Test
    fun serializeSearchRequest() {
        val json = apiMapper.encodeToString(searchRequest)

        assertContains(json, Regex("\"requestType\":\\s*\"search\""))
        assertContains(json, Regex("\"search\":\\s*\"mass\""))
    }

    @Test
    fun deserializeSearchRequest() {
        val json = apiMapper.encodeToString(searchRequest)
        val obj = apiMapper.decodeFromString(json) as UnitSearchRequest

        assertEquals(searchRequest, obj)
    }
}
