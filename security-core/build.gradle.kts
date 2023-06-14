group = "org.xzframework.wx"
plugins {
    id("java-conventions")
    `java-library`
}
dependencies {
    api("org.slf4j:slf4j-api")

    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework.security:spring-security-web")

    implementation("org.apache.commons:commons-lang3")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
}
