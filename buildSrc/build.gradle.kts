plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}
