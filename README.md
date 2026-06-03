# Kotest-Unit

This repository is a kotlin-based library for improving the usability of Kotest.

## Introduction

Only mark unit tests and run them in one command.
Exclude every other test even before instantiation.

## Installation

### Maven Dependency

```kotlin
dependencies {
    testImplementation("blog.katfun:kotest-unit:2.0.0")
}
```

The dependency provides the unit-test marker and Kotest spec filter. It does not register Gradle tasks automatically.
Add a Gradle task like this to run only marked unit tests:

```kotlin
tasks.register<Test>("kotestUnittest") {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform {
        includeEngines("kotest")
    }

    systemProperty("kotest.filter.unit-only", "true")
    systemProperty("kotest.framework.config.fqn", "blog.katfun.global.config.KotestProjectConfig")

    filter { isFailOnNoMatchingTests = false }
}
```

### Gradle Plugin

The Gradle plugin provides a shorter setup:

```kotlin
plugins {
    id("blog.katfun.kotest-unit") version "2.0.0"
}

repositories {
    mavenCentral()
}
```

The plugin adds the Maven dependency and registers `kotestUnittest` automatically. `mavenCentral()` is still required
so Gradle can resolve `blog.katfun:kotest-unit`.

## Marking Tests

### Direct Tags

Use Kotest's `@Tags` annotation with name "unit".

### Meta Tags

Use `@UnitTest` annotation.

## Running Tests

Use `./gradlew kotestUnittest` to run the marked tests.

## Version Compatibility

| Kotest | Kotest-Unit |
|--------|-------------|
| 5.9.1  | 1.0.0       |
| 6.1.11 | 2.0.0       |
