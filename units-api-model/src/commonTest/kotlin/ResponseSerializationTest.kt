package com.crowdproj.units.api.v1

import com.crowdproj.units.api.v1.models.UnitCreateResponse
import com.crowdproj.units.api.v1.models.UnitResponseObject
import com.crowdproj.units.api.v1.models.UnitStatus
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class ResponseSerializationTest {
    private val response = UnitCreateResponse(
        requestId = "2",
        responseType = "create",
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
        val json = apiMapper.encodeToString(response)

        assertContains(json, Regex("\"alias\":\\s*\"gram\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.encodeToString(response)
        val obj = apiMapper.decodeFromString(json) as UnitCreateResponse

        assertEquals(response, obj)
    }
}