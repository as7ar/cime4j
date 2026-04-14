import java.util.Properties

plugins {
    id("java")
    kotlin("jvm") version "2.3.0"
    id("signing")
    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "io.github.astar"
version = "1.0-a3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "cime4j"
            groupId = "io.github.astar"
            version = "1.0-a2"

            from(components["java"])

            pom {
                name.set("cime4j")
                description.set("cime unofficial api for java/kotlin")
                url.set("https://github.com/as7ar/cime4j")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/mit/")
                    }
                }

                developers {
                    developer {
                        id.set("as7ar")
                        name.set("ASTAR")
                        url.set("https://github.com/as7ar")
                    }
                }

                scm {
                    url.set("https://github.com/as7ar/cime4j")
                    connection.set("scm:git:https://github.com/as7ar/cime4j")
                    developerConnection.set("scm:git:https://github.com/as7ar/cime4j")
                }
            }
        }
    }
}