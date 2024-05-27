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
    plugins {
        kotlin("jvm") version "1.9.24"
        kotlin("kapt") version "1.9.24"
        kotlin("plugin.spring") version "1.9.24"
        kotlin("plugin.jpa") version "1.9.24"
        id("org.hibernate.orm") version "6.4.8.Final"
        id("org.springframework.boot") version "3.2.6"
        id("cz.habarta.typescript-generator") version "3.2.1263"
    }
}
