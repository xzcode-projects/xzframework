group = "org.xzframework"
plugins {
    `maven-publish`
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:${versions["spring_boot"]}"))
    constraints {
        api(project(":autoconfigure"))
        api(project(":data-commons"))
        api(project(":data-jpa"))
        api(project(":security-core"))
        api(project(":security-web"))
        api(project(":security-config"))
        api(project(":web"))
        api(project(":wx-mp-sdk"))
        api(project(":wx-mp-spring-security"))
        api(project(":activiti-api"))
        api(project(":activiti-jpa-impl"))
        api(project(":spring-session-ext"))
        api(project(":wx-mp-sdk"))
    }
}

publishing {
    publications {
        create<MavenPublication>("platform") {
            from(components["javaPlatform"])
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
