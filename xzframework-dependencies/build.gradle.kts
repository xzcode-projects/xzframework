group = "org.xzframework"
plugins {
    `maven-publish`
    `java-platform`
}

allprojects {
    version = "3.2.4-SNAPSHOT"
}
javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:${Versions.spring_boot}"))
    constraints {
        api("commons-io:commons-io:${Versions.commons_io}")
        api("org.apache.commons:commons-collections4:${Versions.common_collections}")
        api(project(":autoconfigure"))
        api(project(":data-commons"))
        api(project(":data-jpa"))
        api(project(":security-core"))
        api(project(":web"))
        api(project(":wx-mp-sdk"))
        api(project(":wx-mp-spring-security"))
        api(project(":activiti-api"))
        api(project(":activiti-jpa-impl"))
    }
}

publishing {
    publications {
        create<MavenPublication>("platform") {
            from(components["javaPlatform"])
        }
    }
}
