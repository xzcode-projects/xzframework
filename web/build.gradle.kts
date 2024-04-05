group = "org.xzframework"
plugins {
    id("java-conventions")
}
dependencies {
    api("org.springframework.data:spring-data-jpa")
    api("org.springframework:spring-web")
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
}
