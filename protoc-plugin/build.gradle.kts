plugins {
    id("application")
    checkstyle
}

group = "io.github.protogenerator"
version = "1.0.0-SNAPSHOT"

application {
    mainClass.set("io.github.protogenerator.plugin.StrictProtoGenerator")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":generator-core"))
    implementation("com.google.protobuf:protobuf-java:3.25.3")
}
