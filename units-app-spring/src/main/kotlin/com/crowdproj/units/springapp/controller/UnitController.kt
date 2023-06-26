package com.crowdproj.units.springapp.controller

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.logging.common.MpLoggerProvider
import com.crowdproj.units.mappers.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/unit", produces = [MediaType.APPLICATION_JSON_VALUE])
class UnitController(
    private val processor: MkplUnitProcessor,
    private val loggerProvider: MpLoggerProvider,
) {

    @PostMapping("create")
    suspend fun createUnit(@RequestBody request: String): String =
        process<UnitCreateRequest, UnitCreateResponse>(processor, MkplCommand.CREATE, requestString = request, loggerProvider.logger(UnitController::class), "unit-create")

    @PostMapping("read")
    suspend fun readUnit(@RequestBody request: String): String =
        process<UnitReadRequest, UnitReadResponse>(processor, MkplCommand.READ, requestString = request, loggerProvider.logger(UnitController::class), "unit-read")

    @PostMapping("update")
    suspend fun updateUnit(@RequestBody request: String): String =
        process<UnitUpdateRequest, UnitReadResponse>(processor, MkplCommand.UPDATE, requestString = request, loggerProvider.logger(UnitController::class), "unit-update")

    @PostMapping("delete")
    suspend fun deleteUnit(@RequestBody request: String): String =
        process<UnitDeleteRequest, UnitDeleteResponse>(processor, MkplCommand.DELETE, requestString = request, loggerProvider.logger(UnitController::class), "unit-delete")

    @PostMapping("search")
    suspend fun searchUnit(@RequestBody request: String): String =
        process<UnitSearchRequest, UnitSearchResponse>(processor, MkplCommand.SEARCH, requestString = request, loggerProvider.logger(UnitController::class), "unit-search")

    @PostMapping("suggest")
    suspend fun suggestUnit(@RequestBody request: String): String  =
        process<UnitSuggestRequest, UnitSuggestResponse>(processor, MkplCommand.SUGGEST, requestString = request, loggerProvider.logger(UnitController::class), "unit-suggest")

}