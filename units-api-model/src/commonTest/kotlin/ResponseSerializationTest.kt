package com.crowdproj.units.api.v1

import com.crowdproj.units.api.v1.models.IUnitResponse
import com.crowdproj.units.api.v1.models.UnitCreateResponse
import com.crowdproj.units.api.v1.models.UnitResponseObject
import com.crowdproj.units.api.v1.models.UnitStatus
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json

class ResponseSerializationTest {
    private val response = UnitCreateResponse(
        requestId = "2",
        unit = UnitResponseObject(
            name = "g",
            description = "unit of mass",
            alias = "gram",
            id = "1",
            systemUnitId = "metric",
            status = UnitStatus.CONFIRMED
        )
    )

    @Test
    fun serialize() {
        val json = Json.encodeToString(IUnitResponse.serializer(), response)

        assertContains(json, Regex("\"alias\":\\s*\"gram\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IUnitResponse.serializer(), response)
        val obj = Json.decodeFromString(IUnitResponse.serializer(), json) as UnitCreateResponse

        assertEquals(response, obj)
    }
}