group = "org.xzframework.wx"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.slf4j:slf4j-api")
    api("org.springframework:spring-web")
    api("org.springframework.integration:spring-integration-core")
    api("com.fasterxml.jackson.core:jackson-annotations")
    api("tools.jackson.core:jackson-databind")
}
