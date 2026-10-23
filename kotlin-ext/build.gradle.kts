import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode

group = "org.xzframework"

plugins {
    id("java-conventions")
    kotlin("jvm")
}

tasks {
    kotlin {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmDefault.set(JvmDefaultMode.ENABLE)
        }
    }
}






