# Kotest-Unit

This repository is a kotlin-based library for improving the usability of Kotest.

## Introduction

Only mark unit tests and run them in one command.
Exclude every other test even before instantiation.

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