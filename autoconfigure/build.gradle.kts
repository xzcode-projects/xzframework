group = "org.xzframework.boot"
plugins {
    id("java-conventions")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webmvc")
    compileOnly("org.springframework:spring-webflux")

    compileOnly(project(":data-commons"))
}
