rootProject.name = "crowdproj-units"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val codeGeneratorVersion: String by settings
    val springFrameworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("org.springframework.boot") version springFrameworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

include("units-api-model")
include("units-api-log")
include("units-common")
include("units-mappers")
include("units-mappers-log")

include("units-app-spring")
include("units-app-kafka")

include("units-biz")

include("units-repo-tests")
include("units-repo-in-memory")
include("units-repo-stubs")
include("units-repo-gremlin")

include("units-lib-logging-common")
include("units-lib-logging-kermit")
include("units-lib-logging-logback")

include("units-stubs")
