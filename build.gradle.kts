import java.util.Properties

plugins {
    id("java")
    kotlin("jvm") version "2.3.0"
    id("signing")
    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

val publishProps = Properties()
publishProps.load(File("publish.properties").inputStream())

ext["signing.keyId"] = publishProps["signing.keyId"]
ext["signing.password"] = publishProps["signing.password"]
ext["signing.secretKeyRingFile"] = publishProps["signing.secretKeyRingFile"]

val sonatypeUsername = publishProps["nexusUsername"] as String
val sonatypePassword = publishProps["nexusPassword"] as String

group = "io.github.astar"
version = "1.0-a2"

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

signing {
//    sign(publishing.publications["mavenJava"])
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("MAVEN_USERNAME"))
            password.set(System.getenv("MAVEN_PASSWORD"))
        }
    }
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