package com.crowdproj.units.mappers

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.stubs.MkplStubs
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
            unit = UnitCreateObject(
                name = "name",
                description = "description",
                alias = "alias",
                status = UnitStatus.CONFIRMED
            )
        )

        val context = MkplContext()
        context.fromTransport(req)

        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals("name", context.unitRequest.name)
        assertEquals("description", context.unitRequest.description)
        assertEquals("alias", context.unitRequest.alias)
        assertEquals(MkplUnitStatus.CONFIRMED, context.unitRequest.status)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            unitResponse = MkplUnit(
                name = "name",
                alias = "g",
                description = "description",
                status = MkplUnitStatus.DEPRECATED,
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportUnit() as UnitCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.unit?.name)
        assertEquals("g", req.unit?.alias)
        assertEquals("description", req.unit?.description)
        assertEquals(UnitStatus.DEPRECATED, req.unit?.status)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.title)
    }
}
