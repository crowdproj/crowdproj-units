plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    val kotestVersion: String by project
    val springDocOpenApiUiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val assertjVersion: String by project
    val springmockkVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-actuator") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-ui:$springDocOpenApiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // for spring-boot app
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // transport models
    implementation(project(":units-common"))
    implementation(project(":units-lib-logging-logback"))
    implementation(project(":units-lib-logging-kermit"))

    // API
    implementation(project(":units-api-model"))
    implementation(project(":units-mappers"))

    // Business logic
    implementation(project(":units-biz"))

    // Repository
    implementation(project(":units-repo-stubs"))
    implementation(project(":units-repo-in-memory"))
    implementation(project(":units-repo-gremlin"))

    // Logging
    implementation(project(":units-mappers-log"))
    implementation(project(":units-api-log"))

    // Stubs
    implementation(project(":units-stubs"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(project(":units-stubs"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion") // mockking beans
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(project(":units-repo-in-memory"))
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/units-api-model/specs") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
