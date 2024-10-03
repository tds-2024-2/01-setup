/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    id("com.diffplug.spotless") version "6.25.0"

    id("org.sonarqube") version "5.1.0.4882"

    id("jacoco")

    // id("io.snyk.gradle.plugin.snykplugin") version "0.6.1"
    id("org.owasp.dependencycheck") version "10.0.4"
    // id("dependency.check") version "0.0.6"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

spotless {
  java {
    googleJavaFormat()
    licenseHeaderFile("$rootDir/licenseheader.txt")
    removeUnusedImports()
    // licenseHeader("Test")
    // optional: you can specify a specific version (>= 1.8) and/or switch to AOSP style
    //   and/or reflow long strings
    //   and/or use custom group artifact (you probably don't need this)
    // googleJavaFormat('1.8').aosp().reflowLongStrings().formatJavadoc(false).reorderImports(false).groupArtifact('com.google.googlejavaformat:google-java-format')
  }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacoco")
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            limit {
                minimum = "0.2".toBigDecimal()
            }
        }

        // rule {
        //     isEnabled = false
        //     element = "CLASS"
        //     includes = listOf("org.gradle.*")

        //     limit {
        //         counter = "LINE"
        //         value = "TOTALCOUNT"
        //         maximum = "0.3".toBigDecimal()
        //     }
        // }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}
