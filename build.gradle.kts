import org.gradle.plugins.signing.SigningExtension

plugins {
    `java-library`
    kotlin("jvm") version "2.3.21"
    id("com.ncorti.ktfmt.gradle") version "0.26.0"
    id("com.vanniktech.maven.publish") version "0.36.0"
}

group = "blog.katfun"

version = "1.0.0"

repositories { mavenCentral() }

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

kotlin { jvmToolchain(17) }

ktfmt { kotlinLangStyle() }

tasks.test { useJUnitPlatform() }

tasks.register<Test>("kotestUnittest") {
    group = JavaBasePlugin.VERIFICATION_GROUP
    description =
        "Runs kotest tests with @Tags(\"unit\") attached, filtered through UnitOnlySpecFilter."

    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform() { includeEngines("kotest") }

    systemProperty("kotest.filter.unit-only", "true")

    filter { isFailOnNoMatchingTests = false }
}

mavenPublishing {
    coordinates("blog.katfun", "kotest-unit", "1.0.0")

    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Kotest Unit")
        description.set("A Kotest helper for running only specs marked as unit tests.")
        inceptionYear.set("2026")
        url.set("https://github.com/kchung1995/kotest-unit")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("kchung1995")
                name.set("kchung1995")
                email.set("kchung1995@users.noreply.github.com")
                organization.set("kchung1995")
                organizationUrl.set("https://github.com/kchung1995")
                url.set("https://github.com/kchung1995")
            }
        }

        scm {
            url.set("https://github.com/kchung1995/kotest-unit")
            connection.set("scm:git:https://github.com/kchung1995/kotest-unit.git")
            developerConnection.set("scm:git:ssh://git@github.com/kchung1995/kotest-unit.git")
        }
    }
}

pluginManager.withPlugin("signing") {
    extensions.configure<SigningExtension>("signing") {
        val signingKeyId =
            providers
                .gradleProperty("signingInMemoryKeyId")
                .orElse(providers.gradleProperty("signing.keyId"))
                .orNull
        val signingPassword =
            providers
                .gradleProperty("signingInMemoryKeyPassword")
                .orElse(providers.gradleProperty("signing.password"))
                .orNull
        val signingKey = providers.gradleProperty("signingInMemoryKey").orNull
        val signingKeyFile =
            providers
                .gradleProperty("signingInMemoryKeyFile")
                .orElse(providers.gradleProperty("signing.secretKeyRingFile"))
                .orNull

        when {
            signingKey != null -> useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
            signingKeyFile?.endsWith(".asc") == true ->
                useInMemoryPgpKeys(signingKeyId, file(signingKeyFile).readText(), signingPassword)
        }

        isRequired =
            gradle.startParameter.taskNames.any { taskName ->
                taskName.contains("MavenCentral", ignoreCase = true)
            }
    }
}
