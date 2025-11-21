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
    compileOnly("com.querydsl", "querydsl-jpa", "5.1.0", classifier = "jakarta")

    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

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
