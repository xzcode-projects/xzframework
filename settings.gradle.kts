rootProject.name = "xzframework"
include("data-commons")
include("data-jpa")
include("web")
include("autoconfigure")
include("wx-mp-sdk")
include("wx-mp-spring-security")
include("security-core")
pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
