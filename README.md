# Kotest-Unit

This repository is a kotlin-based library for improving the usability of Kotest.

## Introduction

Only mark unit tests and run them in one command.
Exclude every other test even before instantiation.

## Installation

```kotlin
dependencies {
    testImplementation("blog.katfun:kotest-unit:1.0.0")
}
```

The dependency provides the unit-test marker and Kotest spec filter. Add a Gradle task like this to run only marked
unit tests:

```kotlin
tasks.register<Test>("kotestUnittest") {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform {
        includeEngines("kotest")
    }

    systemProperty("kotest.filter.unit-only", "true")

    filter { isFailOnNoMatchingTests = false }
}
```

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
