group = "org.xzframework.wx"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.slf4j:slf4j-api")
    implementation("org.apache.commons:commons-lang3")
    implementation(project(":wx-mp-sdk"))

    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.security:spring-security-web")
    compileOnly("org.springframework.security:spring-security-config")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}
