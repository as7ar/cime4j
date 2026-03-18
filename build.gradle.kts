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