# Project Overview
This project is a Blog web application built with Kotlin Multiplatform (targeting `wasmJs` for the browser).
It uses Compose Multiplatform for the user interface.

## Tech Stack
- **Language**: Kotlin 2.1.0
- **UI Framework**: Compose Multiplatform 1.7.0
- **Networking**: Ktor 3.0.1
- **Image Loading**: Coil3 (Multiplatform)
- **Async/Concurrency**: Kotlinx Coroutines
- **Markdown Rendering**: Multiplatform Markdown Renderer

## Architecture & Structure
The codebase uses a multi-module architecture:
- `composeApp/`: The main application module containing the entry points.
- `feature:common/`: Reusable Compose UI components, themes, and shared logic.
- `feature:home/`: Home/landing page features for the blog.
- `feature:tech/`: Tech-related blog features/posts.
- `kotlin-js-store/`: JavaScript interop or state management wrappers.