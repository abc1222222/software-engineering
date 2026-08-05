# Software Engineering

Two Java projects built around the same Affine cipher: an **Android application**
that encrypts user-supplied text with validated parameters, and a **string utility
library** implemented against an explicit interface contract.

## Overview

### `android-encryptor/` - SDPEncryptor

A single-activity Android app. The user enters a plaintext string and two integer
cipher parameters, taps **Encrypt Entry Text**, and the ciphertext appears in a
result field. Invalid input is reported inline on the offending field via
`setError`, and the result field is cleared rather than showing a stale value.

### `java-string-utility/` - MyString

A plain Java library implementing `MyStringInterface` - three core string
operations plus an accessor and a validating mutator:

| Method | Behaviour |
|---|---|
| `setString(String s)` | Stores the string. Rejects an empty string and a string containing no letter or digit. |
| `getString()` | Returns the current string, or `null` if never set. |
| `countAlphabeticWords()` | Counts contiguous runs of `[a-zA-Z]`. |
| `encrypt(int a, int b)` | Applies the Affine cipher over a 62-character alphabet. |
| `convertDigitsToNamesInSubstring(int start, int end)` | Replaces digits in the given substring range with their English names. |

A custom unchecked `MyIndexOutOfBoundsException` signals out-of-range substring
bounds.

### The Affine cipher

Both projects implement the same transformation over a 62-symbol alphabet:

```text
E(x) = (a * x + b) mod 62
```

The alphabet index `x` is assigned as:

| Index range | Characters |
|---|---|
| `0`-`9`   | `0`-`9` |
| `10`-`35` | `A`-`Z` |
| `36`-`61` | `a`-`z` |

Characters outside `[0-9A-Za-z]` are passed through unchanged.

Parameter constraints, enforced in both implementations:

- `a` must be **coprime with 62**. Since `62 = 2 * 31`, the code rejects any `a`
  that is even or divisible by 31; otherwise the mapping would not be invertible.
- `b` must satisfy `1 <= b <= 61`.
- Violations raise `IllegalArgumentException` in the library, and surface as an
  inline field error in the app.

## Features

- Affine cipher over a 62-character alphanumeric alphabet with invertibility
  enforced through coprimality checks.
- Interface-driven design: `MyString` implements the `MyStringInterface` contract,
  which documents every precondition and the exception raised when it is violated.
- Custom runtime exception type for substring bounds violations.
- Android UI built with ConstraintLayout and Material Components, with per-field
  inline validation errors.
- Day and night themes, adaptive launcher icons, and Android 13 backup and
  data-extraction rules.

## Directory Structure

```text
software-engineering/
|-- android-encryptor/                       # Gradle project "SDPEncryptor"
|   |-- app/
|   |   |-- build.gradle                     # compileSdk/minSdk/targetSdk 33, Java 17
|   |   |-- proguard-rules.pro
|   |   `-- src/
|   |       |-- main/
|   |       |   |-- AndroidManifest.xml
|   |       |   |-- java/io/github/wangwang11112222/sdpencryptor/
|   |       |   |   `-- MainActivity.java    # UI wiring, validation, Affine cipher
|   |       |   `-- res/                     # layout, themes, colors, strings, icons
|   |       |-- test/                        # local JVM unit test (JUnit 4 stub)
|   |       `-- androidTest/                 # instrumented test (Espresso stub)
|   |-- build.gradle                         # Android Gradle Plugin 8.2.2
|   |-- gradle.properties
|   `-- settings.gradle
|-- java-string-utility/
|   `-- src/io/github/wangwang11112222/stringutility/
|       |-- MyStringInterface.java           # Contract: methods, preconditions, exceptions
|       |-- MyString.java                    # Implementation
|       `-- MyIndexOutOfBoundsException.java # Custom unchecked exception
|-- PROJECT_SUMMARY.md
|-- LICENSE
`-- README.md
```

## Installation

```bash
git clone https://github.com/wangwang11111222/software-engineering.git
cd software-engineering
```

Prerequisites:

- **JDK 17** - the Android module sets both source and target compatibility to 17.
- **Android Studio** (Hedgehog or newer, matching Android Gradle Plugin 8.2.2) and
  **Android SDK Platform 33** for the app.
- **Gradle 8.x** on your `PATH`, only if you intend to build the app from the
  command line - see the caveat below.

## Usage

### Building the Android app

**The Gradle wrapper is not committed to this repository.** There is no `gradlew`,
`gradlew.bat`, or `gradle/wrapper/` directory, so `./gradlew assembleDebug` will
fail with "no such file or directory". Use one of these two routes instead.

*Option A - Android Studio (recommended).* Open the `android-encryptor/` directory
as an existing project. Android Studio provisions a compatible Gradle distribution
itself, syncs the project, and can then build and run it on an emulator or device
running API 33 or later. `minSdk` is 33, so older devices are not supported.

*Option B - generate the wrapper first.* With a local Gradle 8.x installation:

```bash
cd android-encryptor
gradle wrapper                # creates gradlew, gradlew.bat and gradle/wrapper/
./gradlew assembleDebug       # debug APK -> app/build/outputs/apk/debug/
./gradlew test                # local JVM unit tests
./gradlew connectedAndroidTest  # instrumented tests; requires a running device
```

Note that the generated wrapper files are excluded by `.gitignore`; commit them
deliberately if you want them tracked.

### Using the app

1. Enter the text to encrypt in **Entry Text**.
2. Enter `a` in **Arg Input 1** - odd, not a multiple of 31, in `[1, 61]`.
3. Enter `b` in **Arg Input 2** - in `[1, 61]`.
4. Tap **Encrypt Entry Text**. The ciphertext appears in **Entry Text Encrypted**.

Non-numeric or out-of-range parameters mark the corresponding field with an inline
error and clear the result.

### Building the string utility

**`java-string-utility/` has no build file.** There is no `pom.xml`,
`build.gradle`, or wrapper - it is a bare source tree, so compile it directly with
`javac`.

```bash
cd java-string-utility
mkdir -p out
javac -d out src/io/github/wangwang11112222/stringutility/*.java
```

There is no test suite in this repository; see
[Provenance and Licensing](#provenance-and-licensing) for why.

Using the library directly:

```java
MyStringInterface s = new MyString();
s.setString("My numbers are 11, 96, and thirteen");

s.countAlphabeticWords();   // counts contiguous [a-zA-Z] runs
s.encrypt(3, 7);            // Affine cipher, a=3 (coprime with 62), b=7
s.convertDigitsToNamesInSubstring(15, 17);  // digits -> English names in range
```

## Dependencies

**`android-encryptor/`** (from `app/build.gradle`)

| Dependency | Version | Scope |
|---|---|---|
| Android Gradle Plugin | 8.2.2 | build |
| `compileSdk` / `minSdk` / `targetSdk` | 33 | build |
| Java source/target compatibility | 17 | build |
| `androidx.appcompat:appcompat` | 1.4.1 | implementation |
| `com.google.android.material:material` | 1.5.0 | implementation |
| `androidx.constraintlayout:constraintlayout` | 2.1.4 | implementation |
| `junit:junit` | 4.13.2 | testImplementation |
| `androidx.test.ext:junit` | 1.1.5 | androidTestImplementation |
| `androidx.test.espresso:espresso-core` | 3.5.1 | androidTestImplementation |

Dependencies resolve from `google()` and `mavenCentral()`;
`settings.gradle` sets `RepositoriesMode.FAIL_ON_PROJECT_REPOS`. Lint is disabled
and `abortOnError` is off in the app module.

**`java-string-utility/`**

- JDK 17 (no `module-info`, no external runtime dependencies).

## Provenance and Licensing

Everything in this repository is the author's own work and is released under the
[MIT License](LICENSE).

The string utility originated as a coursework exercise. Two files in the original
working copy were supplied by the course and carried redistribution restrictions,
so they are **deliberately not published here**:

| Original file | Why it is absent |
|---|---|
| The course-supplied `MyStringInterface.java` | Carried a third-party copyright notice and a do-not-alter clause. Replaced by an independently written interface with the same method signatures and original documentation. |
| The course-supplied `MyStringTest.java` | Stated that it "should NOT be posted in any public repositories, even after the class has ended". Removed; no replacement test suite is published. |

`MyStringInterface.java` as it appears in this repository was written from scratch.
It keeps the same method signatures - which are a functional API, not a creative
work - but the documentation, wording, and structure are original, and the
course-supplied copyright constant was dropped along with the `setString` check
that referenced it.

If you fork this repository, do not reintroduce course-supplied material into it.

## Notes

- **The Gradle wrapper is not committed**, so `./gradlew` does not work out of the
  box. Open the project in Android Studio, or run `gradle wrapper` first.
- **`java-string-utility/` has no build system and no tests.** Compile it manually
  as shown above.
- The Android module's `test/` and `androidTest/` source sets contain only the
  generated Android Studio stubs, so the cipher logic in `MainActivity.java` is not
  covered by automated tests either.
- The cipher logic is duplicated between `MainActivity.java` and `MyString.java`
  rather than shared through a common module. This is left as-is, since the two
  projects are separate coursework deliverables.
- No build outputs, APKs, or test reports are committed.

## License

Released under the [MIT License](LICENSE). See
[Provenance and Licensing](#provenance-and-licensing) for the material that was
deliberately excluded from this repository.
