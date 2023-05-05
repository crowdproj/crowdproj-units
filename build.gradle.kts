import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm")
}

val javaVersion: String by project

group = "com.crowdproj.units"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }
    tasks.withType<KotlinJvmCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }
}
