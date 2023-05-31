repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

plugins {
    `java-library`
    `maven-publish`
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(platform("commons-io:commons-io:${Versions.commons_io}"))
    implementation(platform("org.apache.commons:commons-collections4:${Versions.commonCollections4}"))
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
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

