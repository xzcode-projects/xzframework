group = "org.xzframework.session"
plugins {
    id("java-conventions")
    `java-library`
}
dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework:spring-core")
    compileOnly("org.springframework:spring-beans")
    api("org.springframework.session:spring-session-core")
}
