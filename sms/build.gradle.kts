import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.xzframework.sms"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.slf4j:slf4j-api")
    compileOnly("com.aliyun:alibabacloud-dysmsapi20170525:2.0.24")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}
