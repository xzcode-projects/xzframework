group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.springframework.data:spring-data-commons")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webflux")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }
}
