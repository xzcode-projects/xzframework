repositories {
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

    compileJava {
        options.encoding = "UTF-8"
    }

    java {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
        withSourcesJar()
    }
}

dependencies {
    implementation(platform("commons-io:commons-io:${Versions.commons_io}"))
    implementation(platform("org.apache.commons:commons-collections4:${Versions.common_collections}"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${Versions.spring_boot}"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        versionMapping {
            usage("java-api") {
                fromResolutionOf("runtimeClasspath")
            }
            usage("java-runtime") {
                fromResolutionResult()
            }
        }

        pom.withXml {
            val root = asNode()
            val list = root["dependencyManagement"] as groovy.util.NodeList
            list.forEach { root.remove(it as groovy.util.Node) }
        }
    }

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

