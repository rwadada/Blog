# Task Completion Guidelines
When a task is completed in this project, ensure that you:
1. **Build the project**: Run `./gradlew build` to ensure no compile errors were introduced across any targets (especially the Wasm JS targets).
2. **Review features**: Check if new dependencies should be added to `libs.versions.toml` instead of directly to build scripts.
3. **Run tests**: Execute `./gradlew check` if the task involves business logic changes.
4. **Clean up**: Remove any debug logs or temporary files created during development.