plugins {
    id("java-library")
    checkstyle
}

group = "io.github.protogenerator"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.25.3")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "../generated-java", "../generated")
        }
    }
}
