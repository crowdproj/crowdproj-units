package com.crowdproj.marketplace.units.api.v1

import com.crowdproj.marketplace.units.api.v1.models.*
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
            id = "1",
            unit = "Kg",
            description = "unit of mass",
            alias = "gram",
            status = UnitStatus.CONFIRMED,
            conversion = UnitConversionParameters(),
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.encodeToString(response)

        assertContains(json, Regex("\"unit\":\\s*\"Kg\""))
        assertContains(json, Regex("\"alias\":\\s*\"gram\""))
        assertContains(json, Regex("\"description\":\\s*\"unit of mass\""))
        assertContains(json, Regex("\"status\":\\s*\"${UnitStatus.CONFIRMED}\""))
        assertContains(json, Regex("\"conversion\":\\s*\\{\"A\":1.0,\"B\":0.0,\"C\":1.0,\"D\":0.0}"))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.encodeToString(response)
        val obj = apiMapper.decodeFromString(json) as UnitCreateResponse

        assertEquals(response, obj)
    }
}
