plugins {
    id("java")
}

group = "io.github.protogenerator"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(project(":generator-core"))
    testImplementation(project(":protoc-plugin"))
    testImplementation("com.google.protobuf:protobuf-java:4.28.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}
