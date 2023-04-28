package com.crowdproj.units.springapp.controller

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.mappers.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/unit")
class UnitController {

    @PostMapping("create")
    suspend fun createUnit(@RequestBody request: UnitCreateRequest): UnitCreateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitResponse = MkplUnitStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    suspend fun readUnit(@RequestBody request: UnitReadRequest): UnitReadResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitResponse = MkplUnitStub.get()
        return context.toTransportRead()
    }

    @PostMapping("update")
    suspend fun updateUnit(@RequestBody request: UnitUpdateRequest): UnitUpdateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitResponse = MkplUnitStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    suspend fun deleteUnit(@RequestBody request: UnitDeleteRequest): UnitDeleteResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitResponse = MkplUnitStub.get()
        return context.toTransportDelete()
    }

    @PostMapping("search")
    suspend fun searchUnit(@RequestBody request: UnitSearchRequest): UnitSearchResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitsResponse = mutableListOf(MkplUnitStub.get())
        return context.toTransportSearch()
    }

    @PostMapping("suggest")
    suspend fun suggestUnit(@RequestBody request: UnitSuggestRequest): UnitSuggestResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.unitResponse = MkplUnitStub.get()
        return context.toTransportSuggest()
    }
}