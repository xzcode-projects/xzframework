group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.springframework.data:spring-data-commons")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webflux")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}
