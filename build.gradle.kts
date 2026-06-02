plugins {
    `java-library`
    kotlin("jvm") version "2.3.21"
}

group = "blog.katfun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotestVersion = "5.9.1"

dependencies {
    api("io.kotest:kotest-framework-api:$kotestVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // kotest
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("kotestUnittest") {
    group = JavaBasePlugin.VERIFICATION_GROUP
    description = "Runs kotest tests with @Tags(\"unit\") attached, filtered through UnitOnlySpecFilter."

    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform() {
        includeEngines("kotest")
    }

    systemProperty("kotest.filter.unit-only", "true")

    filter { isFailOnNoMatchingTests = false }
}
