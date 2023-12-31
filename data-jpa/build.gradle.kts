group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("com.querydsl:querydsl-core")

    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

