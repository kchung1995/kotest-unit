# Kotest-Unit

This repository is a kotlin-based library for improving the usability of Kotest.

## Introduction

Only mark unit tests and run them in one command.
Exclude every other test even before instantiation.

## Installation

### Maven Dependency

```kotlin
dependencies {
    testImplementation("blog.katfun:kotest-unit:${selectedVersion}")
}
```

See [Version Compatibility](#version-compatibility) for choosing the appropriate version based on your Kotest version.

### Gradle Task

#### Gradle Plugin (Currently pending for Gradle team's approval)

Add a Gradle plugin for easy use.

```kotlin
plugins {
    id("blog.katfun.kotest-unit") version ${selectedVersion}
}

repositories {
    mavenCentral()
}
```

#### Manual

The dependency provides the unit-test marker and Kotest spec filter. It does not register Gradle tasks automatically.
Add a Gradle task that fits your version of Kotest to run only marked unit tests.

Check [KotestUnitPlugin](./blog/katfun/gradle/KotestUnitPlugin).

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
