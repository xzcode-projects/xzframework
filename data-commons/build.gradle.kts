group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    compileOnly("org.springframework.data:spring-data-commons")
    compileOnly("org.springframework:spring-web")
}
