# Code Style & Conventions
- **Language**: Kotlin
- **Style**: Official Kotlin Coding Conventions (`kotlin.code.style=official` is set in `gradle.properties`).
- **UI**: Compose Multiplatform declarative UI principles. Group UI components in their respective `feature:` modules.
- **Dependencies**: Managed centrally via `gradle/libs.versions.toml`. Add new dependencies there rather than directly in `build.gradle.kts`.
- **Asynchrony**: Use suspend functions and Kotlin Coroutines. Do not use blocking calls in the UI thread.