group = "org.xzframework.session"
plugins {
    id("java-conventions")
    `java-library`
}
dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    api("org.springframework.session:spring-session-core")
}
