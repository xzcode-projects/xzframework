group = "org.xzframework.security"
plugins {
    id("java-conventions")
    kotlin("jvm")
    `java-library`
}
dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    api("org.springframework.security:spring-security-web")
}
