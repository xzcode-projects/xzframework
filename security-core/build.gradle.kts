group = "org.xzframework.security"
plugins {
    id("java-conventions")
    kotlin("jvm")
    `java-library`
}
dependencies {
    api("org.slf4j:slf4j-api")
    api("jakarta.servlet:jakarta.servlet-api")
    api("org.springframework:spring-web")
    api("org.springframework.security:spring-security-web")

    implementation("org.apache.commons:commons-lang3")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
}
