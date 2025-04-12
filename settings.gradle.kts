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
include("spring-session-ext")
include("security-web")
include("security-config")
include("sms")
include("xzframework-dependencies")

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "1.9.25"
        kotlin("kapt") version "1.9.25"
        kotlin("plugin.spring") version "1.9.25"
        kotlin("plugin.jpa") version "1.9.25"
        id("org.hibernate.orm") version "6.6.11.Final"
        id("org.springframework.boot") version "3.4.4"
        id("cz.habarta.typescript-generator") version "3.2.1263"
    }
}
