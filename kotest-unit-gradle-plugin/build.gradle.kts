import org.gradle.plugin.compatibility.compatibility

plugins {
    kotlin("jvm") version "2.3.21"
    id("com.ncorti.ktfmt.gradle") version "0.26.0"
    id("com.gradle.plugin-publish") version "2.1.1"
}

group = "blog.katfun"

version = "2.0.0"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation(gradleTestKit())
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin { jvmToolchain(17) }

ktfmt { kotlinLangStyle() }

tasks.test { useJUnitPlatform() }

gradlePlugin {
    website.set("https://github.com/kchung1995/kotest-unit")
    vcsUrl.set("https://github.com/kchung1995/kotest-unit")

    plugins {
        create("kotestUnitPlugin") {
            id = "blog.katfun.kotest-unit"
            implementationClass = "blog.katfun.gradle.KotestUnitPlugin"
            displayName = "Kotest Unit Gradle Plugin"
            description = "Adds the Kotest Unit dependency and registers a kotestUnittest task."
            tags.set(listOf("kotest", "kotlin", "testing", "unit-test"))
            compatibility { features { configurationCache = true } }
        }
    }
}
