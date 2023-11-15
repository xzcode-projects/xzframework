group = "org.xzframework.activiti"
plugins {
    id("java-conventions")
    `java-library`
}
dependencies {
    api("org.slf4j:slf4j-api")
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")

    implementation(project(":activiti-api"))

    implementation("org.apache.commons:commons-lang3")
}
