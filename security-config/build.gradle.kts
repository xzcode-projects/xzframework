group = "org.xzframework.security"
plugins {
    id("java-conventions")
    kotlin("jvm")
    `java-library`
}
dependencies {
    api("org.springframework.security:spring-security-web")
    api("org.springframework.security:spring-security-config")
}
