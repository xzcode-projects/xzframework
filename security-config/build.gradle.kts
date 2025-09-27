group = "org.xzframework.security"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.springframework.security:spring-security-web")
    api("org.springframework.security:spring-security-config")
}
