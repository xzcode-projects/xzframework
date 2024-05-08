import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.xzframework.wx"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.slf4j:slf4j-api")
    api("org.springframework:spring-web")
    api("org.springframework.integration:spring-integration-core")
    api("com.fasterxml.jackson.core:jackson-databind")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}
