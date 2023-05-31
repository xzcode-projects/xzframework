group = "org.xzframework"
plugins {
    id("java-conventions")
}
dependencies {
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.springframework:spring-web")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
}
