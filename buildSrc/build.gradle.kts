import java.io.FileReader
import java.util.*

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
}
val versions = Properties().apply { load(FileReader(file("src/main/resources/versions.properties"))) }
tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${versions["spring_boot"]}")
}
