plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.9.25")
}

tasks {

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.AMAZON
        }
    }
    kotlin {
        jvmToolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.AMAZON
        }
    }
}

