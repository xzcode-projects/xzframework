group = "org.xzframework.sms"

plugins {
    id("java-conventions")
    kotlin("jvm")
}
dependencies {
    api("org.slf4j:slf4j-api")
    compileOnly("com.aliyun:alibabacloud-dysmsapi20170525:2.0.24")
}
