repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

plugins {
    `java-library`
    `maven-publish`
}

tasks {

    test {
        useJUnitPlatform()
    }

    java {
        compileJava {
            options.encoding = "UTF-8"
        }
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${versions["spring_boot"]}"))
}

publishing {
    repositories {
        // 发布到自己的maven私有仓库
        maven {
            val releasesRepoUrl = "https://packages.aliyun.com/maven/repository/2124183-release-zS40pt"
            val snapshotsRepoUrl = "https://packages.aliyun.com/maven/repository/2124183-snapshot-jg10er"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = "5f213c551d62073ceeb332b2"
                password = "NC=d3W)28)]W"
            }
        }
    }
}

