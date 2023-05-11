package com.crowdproj.marketplace.units.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            prettyPrint = true
        })
    }

    routing {
        route("v1") {
            v1Unit()
        }
        get("/") {
            call.respondText("Hello world!")
        }
    }
}
