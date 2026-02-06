group = "org.xzframework.data"
plugins {
    id("java-conventions")
}
dependencies {
    api("org.springframework.data:spring-data-commons")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webflux")
}
