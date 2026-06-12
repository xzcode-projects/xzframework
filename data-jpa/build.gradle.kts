group = "org.xzframework.data"
plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.springframework.data:spring-data-jpa")

    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("com.querydsl:querydsl-core")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("org.hibernate.orm:hibernate-core")
    compileOnly("io.github.openfeign.querydsl:querydsl-jpa:7.3.0")

    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:7.3.0:jakarta")

    // 添加JUnit 5测试依赖
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.1")
}

// 添加测试任务配置
tasks.test {
    useJUnitPlatform()
}
