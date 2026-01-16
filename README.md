# Build Instructions

This document outlines the steps required to compile and build the project artifact from source using the provided Gradle wrapper.

## Prerequisites

* **Java Development Kit (JDK) 21** or higher must be installed and configured in your system `PATH`.

## Compilation Steps

To build the project, open a terminal or command prompt in the project's root directory and execute the build task.

### Windows

```bash
gradlew build
```

### Linux / macOS
First, ensure the wrapper script has execution permissions:

```bash
chmod +x gradlew
```
Then, run the build command:

```bash
./gradlew build
```

## Build Artifacts
Upon successful compilation, the final artifact (JAR file) will be located in the following directory:

```
build/libs/
```

## Troubleshooting
If you encounter issues during the build process, you can try cleaning the project cache before rebuilding:

```bash
# Windows
gradlew clean build

# Linux / macOS
./gradlew clean build
```