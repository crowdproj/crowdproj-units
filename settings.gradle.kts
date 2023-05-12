rootProject.name = "crowdproj-units"

pluginManagement {
    val kotlinVersion: String by settings
    val ktorPluginVersion: String by settings
    val bmuschkoVersion: String by settings
    val openapiVersion: String by settings
    val crowdProjBaseApiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("com.crowdproj.generator") version crowdProjBaseApiVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

include("units-common")
include("units-api")
include("units-mappers")

include("units-app-ktor")
include("units-stubs")
