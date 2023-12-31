rootProject.name = "xzframework"
include("data-commons")
include("data-jpa")
include("web")
include("autoconfigure")
include("wx-mp-sdk")
include("wx-mp-spring-security")
include("security-core")
include("activiti-api")
project(":activiti-api").projectDir = file("activiti/activiti-api")
include("activiti-jpa-impl")
project(":activiti-jpa-impl").projectDir = file("activiti/activiti-jpa-impl")
pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
