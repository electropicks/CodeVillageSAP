import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

group = "org.codevillage"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.tmatesoft.svnkit:svnkit:1.10.11")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.4.8")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.7.0.202309050840-r")
    implementation("com.github.javaparser:javaparser-core:3.25.7")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("org.projectlombok:lombok:1.18.28")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.jfree:jfreechart:1.5.3")
    implementation("org.jfree:jcommon:1.0.23")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")


    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed") //, "standardOut", "standardError"

        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true

        showStandardStreams = false
    }
}
