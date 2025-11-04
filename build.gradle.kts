plugins {
    kotlin("multiplatform") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("maven-publish")
    id("signing")
}

group = "uk.co.conoregan"
version = "3.0.0"

repositories {
    mavenCentral()
}

kotlin {
    // JVM target for backwards compatibility
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    // JavaScript target
    js(IR) {
        browser {
            testTask {
                enabled = true
            }
        }
        nodejs {
            testTask {
                enabled = true
            }
        }
    }

    // Native targets
    // Apple platforms
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    // Linux
    linuxX64()
    linuxArm64()

    // Windows
    mingwX64()

    // Common source sets
    sourceSets {
        val ktorVersion = "3.0.0"
        val serializationVersion = "1.7.3"
        val coroutinesVersion = "1.9.0"
        val datetimeVersion = "0.6.1"

        val commonMain by getting {
            dependencies {
                // Ktor client for HTTP requests
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                // Kotlin serialization for JSON
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                // DateTime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.5.8")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:5.11.0")
                implementation("io.mockk:mockk:1.13.13")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val appleMain by creating {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting { dependsOn(appleMain) }
        val macosArm64Main by getting { dependsOn(appleMain) }
        val iosX64Main by getting { dependsOn(appleMain) }
        val iosArm64Main by getting { dependsOn(appleMain) }
        val iosSimulatorArm64Main by getting { dependsOn(appleMain) }
        val watchosArm32Main by getting { dependsOn(appleMain) }
        val watchosArm64Main by getting { dependsOn(appleMain) }
        val watchosX64Main by getting { dependsOn(appleMain) }
        val watchosSimulatorArm64Main by getting { dependsOn(appleMain) }
        val tvosArm64Main by getting { dependsOn(appleMain) }
        val tvosX64Main by getting { dependsOn(appleMain) }
        val tvosSimulatorArm64Main by getting { dependsOn(appleMain) }

        val linuxX64Main by getting { dependsOn(nativeMain) }
        val linuxArm64Main by getting { dependsOn(nativeMain) }
        val mingwX64Main by getting { dependsOn(nativeMain) }

        // Configure apple source sets to use darwin HTTP client
        configure(listOf(
            macosX64Main, macosArm64Main,
            iosX64Main, iosArm64Main, iosSimulatorArm64Main,
            watchosArm32Main, watchosArm64Main, watchosX64Main, watchosSimulatorArm64Main,
            tvosArm64Main, tvosX64Main, tvosSimulatorArm64Main
        )) {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }

        // Configure linux/mingw source sets
        configure(listOf(linuxX64Main, linuxArm64Main, mingwX64Main)) {
            dependencies {
                implementation("io.ktor:ktor-client-curl:$ktorVersion")
            }
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("themoviedbapi")
                description.set("A Kotlin Multiplatform SDK for The Movie Database (TMDb) API v3")
                url.set("https://github.com/c-eg/themoviedbapi")

                licenses {
                    license {
                        name.set("BSD 2-Clause License")
                        url.set("https://github.com/c-eg/themoviedbapi/blob/master/LICENCE.txt")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/c-eg/themoviedbapi.git")
                    url.set("https://github.com/c-eg/themoviedbapi.git")
                }

                developers {
                    developer {
                        id.set("holgerbrandl")
                        name.set("Holger Brandl")
                        email.set("holgerbrandl@gmail.com")
                    }

                    developer {
                        id.set("c-eg")
                        name.set("Conor Egan")
                        email.set("17conoregan@gmail.com")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

if (project.hasProperty("signing.keyId") &&
    project.hasProperty("signing.password") &&
    project.hasProperty("signing.secretKeyRingFile")) {
    signing {
        sign(publishing.publications)
    }
}
