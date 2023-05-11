package com.crowdproj.marketplace.units.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.crowdproj.marketplace.units.app.controllers.*

fun Route.v1Unit() {
    route("unit") {
        post("create") {
            call.createUnit()
        }
        post("read") {
            call.readUnit()
        }
        post("update") {
            call.updateUnit()
        }
        post("delete") {
            call.deleteUnit()
        }
        post("search") {
            call.searchUnits()
        }
    }
}
