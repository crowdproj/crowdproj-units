import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
}

val JVM_TARGET = "11"

group = "com.crowdproj.marketplace.units"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JVM_TARGET
    }
    tasks.withType<KotlinJvmCompile> {
        kotlinOptions.jvmTarget = JVM_TARGET
    }
}

tasks {
    @Suppress("UNUSED_VARIABLE")
    val deploy: Task by creating {
        dependsOn("build")
    }
}
