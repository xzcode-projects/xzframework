group = "org.xzframework.security"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    api("org.springframework.security:spring-security-web")
    api("org.springframework.security:spring-security-config")
    api(project(":security-web"))
}
