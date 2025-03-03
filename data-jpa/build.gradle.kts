group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
    `java-library`
}
dependencies {
    api("org.springframework.data:spring-data-jpa")

    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("com.querydsl:querydsl-core")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("com.querydsl", "querydsl-jpa", "5.1.0", classifier = "jakarta")

    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

