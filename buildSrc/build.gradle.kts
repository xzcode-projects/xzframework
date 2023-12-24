plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    maven("https://maven.aliyun.com/repository/public")
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.2.1")
}



tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }
}
