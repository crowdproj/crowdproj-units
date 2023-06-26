package com.crowdproj.units.springapp.api.controller

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.mappers.*
import com.crowdproj.units.repo.gremlin.UnitRepoGremlin
import com.crowdproj.units.springapp.config.CorConfig
import com.crowdproj.units.springapp.controller.UnitController
import com.ninjasquad.springmockk.MockkBean
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(UnitController::class, CorConfig::class)
internal class UnitControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MkplUnitProcessor

    @MockkBean
    private lateinit var repo: UnitRepoGremlin

    @Test
    @Disabled("keeps throwing kotlinx.serialization.json.internal.JsonDecodingException: " +
            "Unexpected JSON token at offset 2: Encountered an unknown key 'responseType' at path: $" +
            "Seems it can't parse response body correctly. Real response example at line 113")

    fun createUnit() = testStubUnit(
        "/unit/create",
        Json.encodeToString(
            IUnitRequest.serializer(), UnitCreateRequest(
                requestId = "1",
                unit = UnitCreateObject(
                    name = "Test Create Unit",
                    description = "some test unit",
                    alias = "a",
                    status = UnitStatus.CONFIRMED
                ),
                debug = UnitDebug(
                    mode = UnitRequestDebugMode.STUB,
                    stub = UnitRequestDebugStubs.SUCCESS
                )
            )
        ),
        MkplContext().toTransportCreate()
    )

    @Test
    @Disabled
    fun readUnit() = testStubUnit(
        "/unit/read",
        UnitReadRequest(),
        MkplContext().toTransportRead()
    )

    @Test
    @Disabled
    fun updateUnit() = testStubUnit(
        "/unit/update",
        UnitUpdateRequest(),
        MkplContext().toTransportUpdate()
    )

    @Test
    @Disabled
    fun deleteUnit() = testStubUnit(
        "/unit/delete",
        UnitDeleteRequest(),
        MkplContext().toTransportDelete()
    )

    @Test
    @Disabled
    fun searchUnit() = testStubUnit(
        "/unit/search",
        UnitSearchRequest(),
        MkplContext().toTransportSearch()
    )

    @Test
    @Disabled
    fun suggestUnit() = testStubUnit(
        "/unit/suggest",
        MkplUnitStub.get(),
        MkplContext().toTransportSuggest()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubUnit(
        url: String,
        requestObj: Req,
        responseObj: Res
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)

            /*.json(
                """
                { 
                  "responseType": ${url.substringAfter("/unit")},
                  "requestId": "1",
                  "result": "error",
	              "unit": {
		            "name": "Test Create Unit",
                    "description": "some test unit",
		            "alias": "a",
		            "id": "123",
		            "status": "confirmed"
	              }
                }
                """.trimIndent()
            )*/
    }
}