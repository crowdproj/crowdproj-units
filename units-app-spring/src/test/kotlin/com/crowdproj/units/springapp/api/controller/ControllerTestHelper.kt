package com.crowdproj.units.springapp.api.controller

inline fun <reified Req : Any> getJsonOutputDependingOnResponseClass(
    responseType: String,
    responseClassSimpleName: Req,
): String =
    "{" +
        "\"_\":\"com.crowdproj.units.api.v1.models.${responseClassSimpleName}\"," +
        "\"responseType\":\"${responseType}\"," +
        "\"requestId\":null," +
        "\"result\":\"error\"," +
        "\"errors\":null," +
        (if (responseType != "search")
            "\"unit\":{\"name\":null,\"description\":null,\"alias\":null,\"id\":null,\"lock\":null,\"systemUnitId\":null,\"status\":\"none\"}"
        else
            "\"units\":null") +
    "}"
