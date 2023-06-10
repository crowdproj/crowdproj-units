package com.crowdproj.units.springapp.api.controller

import com.crowdproj.units.api.v1.models.*
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.mappers.*
import com.crowdproj.units.springapp.config.CorConfig
import com.crowdproj.units.springapp.controller.UnitController
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(UnitController::class, CorConfig::class)
internal class UnitControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MkplUnitProcessor

    @Test
    fun createUnit() = testStubUnit(
        "/unit/create",
        UnitCreateRequest(),
        MkplContext().apply { unitResponse = MkplUnitStub.get() }.toTransportCreate()
    )

    @Test
    fun readUnit() = testStubUnit(
        "/unit/read",
        UnitReadRequest(),
        MkplContext().toTransportRead()
    )

    @Test
    fun updateUnit() = testStubUnit(
        "/unit/update",
        UnitUpdateRequest(),
        MkplContext().toTransportUpdate()
    )

    @Test
    fun deleteUnit() = testStubUnit(
        "/unit/delete",
        UnitDeleteRequest(),
        MkplContext().toTransportDelete()
    )

    @Test
    fun searchUnit() = testStubUnit(
        "/unit/search",
        UnitSearchRequest(),
        MkplContext().toTransportSearch()
    )

    @Test
    fun suggestUnit() = testStubUnit(
        "/unit/suggest",
        UnitSuggestRequest(),
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
            .expectBody<String>()
            .value {
                println("RESPONSE: $it")
                Assertions.assertThat(it).isEqualTo(getJsonOutputDependingOnResponseClass(url.substringAfter("/unit/"), responseObj::class.java.simpleName))
            }
        coVerify { processor.exec(any()) }
    }
}