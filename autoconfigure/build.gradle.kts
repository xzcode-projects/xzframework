group = "org.xzframework.boot"
plugins {
    id("java-conventions")
}
dependencies {
    api("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webmvc")
    compileOnly("org.springframework:spring-webflux")
    compileOnly("com.fasterxml.jackson.core:jackson-databind")
    compileOnly(project(":data-commons"))
}
