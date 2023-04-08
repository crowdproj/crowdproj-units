rootProject.name = "crowdproj-units"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings


    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("units-api-model")
include("units-common")
include("units-mappers")
