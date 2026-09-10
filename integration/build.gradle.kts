group = "org.xzframework"
plugins {
    id("java-conventions")
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.1")
}
