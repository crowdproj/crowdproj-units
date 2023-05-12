package com.crowdproj.marketplace.units.mappers

import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.models.*
import com.crowdproj.marketplace.units.common.stubs.UntsStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = UnitCreateRequest(
            requestId = "1234",
            debug = UnitDebug(
                mode = UnitRequestDebugMode.STUB,
                stub = UnitRequestDebugStubs.SUCCESS
            ),
            params = UnitCreateParams(
                unit = "name",
                description = "description",
                alias = "alias",
                status = UnitStatus.CONFIRMED,
                conversion = UnitConversionParameters(B = 1.0),
                systemUnitId = "2"
            )
        )

        val context = UntsContext()
        context.fromTransport(req)

        assertEquals(UntsWorkMode.STUB, context.workMode)
        assertEquals(UntsStubs.SUCCESS, context.stubCase)
        assertEquals("name", context.unitRequest.unit)
        assertEquals("alias", context.unitRequest.alias)
        assertEquals(UntsUnitStatus.CONFIRMED, context.unitRequest.status)
        assertEquals(UntsConversionParameters(B = 1.0), context.unitRequest.conversion)
        assertEquals(UntsUnitId("2"), context.unitRequest.systemUnitId)
        assertEquals("description", context.unitRequest.description)
    }

    @Test
    fun toTransport() {
        val context = UntsContext(
            requestId = UntsRequestId("1234"),
            command = UntsCommand.CREATE,
            unitResponse = UntsUnit(
                unit = "name",
                alias = "gram",
                description = "description",
                status = UntsUnitStatus.DEPRECATED,
                conversion = UntsConversionParameters(A = 2.0),
                systemUnitId = UntsUnitId("1"),
            ),
            errors = mutableListOf(
                UntsError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = UntsState.RUNNING,
        )

        val req = context.toTransport() as UnitCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.unit.unit)
        assertEquals("gram", req.unit.alias)
        assertEquals(UnitStatus.DEPRECATED, req.unit.status)
        assertEquals(UnitConversionParameters(A = 2.0), req.unit.conversion)
        assertEquals("description", req.unit.description)
        assertEquals("1", req.unit.systemUnitId)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.message)
    }
}
