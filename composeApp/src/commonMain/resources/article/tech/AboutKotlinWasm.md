# About Kotlin/Wasm and the technology of this blog
## What is Kotlin/Wasm?
Kotlin/Wasm is one of the targets provided in Kotlin Multiplatform. Previously, when building web applications with Kotlin Multiplatform, Kotlin/JS was the only choice. However, a new alternative has been developed called Kotlin/Wasm.

Despite being in the early stages of development, Kotlin/Wasm has shown superior performance compared to Kotlin/JS. This is evident in its usage in Compose for Web, where Kotlin/Wasm has been employed. With the alpha version released at the end of last year, it is anticipated that this technology will gain increasing attention in the future.

![wasm performance](images/wasm-performance-compose.png)

Currently, it is in a state where it operates on Firefox and Chrome.

## Browser API support
Since Kotlin/Wasm provides DOM manipulation and fetch processing from WebAPIs in Kotlin as standard, it allows for the implementation of these processes without the need for new definitions specific to Kotlin. Furthermore, these functionalities utilize interoperability features with JavaScript, enabling the definition of custom functionalities and the invocation of Kotlin from JavaScript.

## Compose for Web
When creating web applications using Kotlin/Wasm exclusively, Compose for Web is an indispensable element. Here's an overview:
Compose for Web is the web adaptation of Jetpack Compose, a Kotlin-based UI toolkit primarily used for building user interfaces in Android applications. With Compose for Web, developers can declaratively construct the user interface of web applications using Kotlin code.

Compose for web used to be HTML-based approach.
This allowed the Compose runtime to render HTML elements. While technically cool and leveraging the Compose runtime, it required binding to platform-specific UI elements. However, this added complexity to the setup and seemed to reduce the potential for technology adoption as a project.

In contrast, the new Compose for Web has transitioned to directly drawing UI using SKIA.
Similar to Compose Multiplatform for platforms other than Android, it uses SKIA on Canvas for rendering on HTML.
This means that from the perspective of Android engineers, there are fewer technological differences when dealing with the web, making it a highly effective technology for Android engineers involved in web development.

When we look at the configuration of the screen that is currently being implemented, it appears as shown in the diagram below.
![compose for web sample](images/compose_for_web_sample_image.png)

## The technology stack used in this blog
As you might have guessed from the previous explanations, this blog is created using Compose for Web (Kotlin/Wasm). Now, let's delve into how to build an application with Compose for Web and share some tips along the way.

## Initialize project
Essentially, the process is similar to using Compose in an Android project. However, there are additional elements to consider. Let's delve into those.

#### project build.gradle.kts
```kts
plugins {
    alias("org.jetbrains.compose:1.6.0-alpha01") apply(false)
    alias("org.jetbrains.kotlin.multiplatform:1.9.21") apply(false)
}
```

In the project's build.gradle file, very few settings are required. Since we are not running an Android project simultaneously, the configuration is specialized for Compose for Web.
Here, we load the plugins for Compose and multiplatform usage.

#### app/build.gradle.kts
```kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(project.projectDir.path)
                        add(project.projectDir.path + "/commonMain/")
                        add(project.projectDir.path + "/wasmJsMain/")
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
    }
}

compose.experimental {
    web.application {}
}
```

This part closely resembles an Android project. In the definition section for the Android platform, we specify wasmJs, and within it, we include settings tailored for Compose for Web:

- outputFileName: The name of the file produced after the build, which is converted to JavaScript.
- under devServer section : This specifies the location of files to be loaded as part of the project.
- sourceSets : Define the libraries to be loaded for each supported platform. commonMain is shared across all platforms.

#### app/wasmJsMain/resources/index.html
```html
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="application/javascript" src="skiko.js"></script>
    <script type="application/javascript" src="composeApp.js"></script>
</head>
<body>
<canvas id="ComposeTarget"></canvas>
</body>
</html>
```
#### app/wasmJsMain/kotlin/app.kt
```kotlin
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        App()
    }
}
```
#### app/commonMain/kotlin/App.kt
```kotlin
@Composable
fun App() {
    MaterialTheme {
        Text(text = "Hello World!")
    }
}
```
The index.html serves as the foundation for rendering the application created with Compose for Web. As a rule, it's necessary to load skiko.js, which is utilized for rendering.
The composeApp.js specifies the output defined in app/build.gradle.kts. By loading this, it enables the functions defined in Kotlin to be accessible from the web. The canvasid within the body tag must match the definition of the app mentioned later, as it determines the content to be rendered based on this ID.
The app.kt in wasmJsMain is responsible for associating the content to be rendered with HTML in Compose for Web. The canvasElementId in the CanvasBasedWindow function must be specified with the canvasid defined in index.html.

The App.kt in commonMain serves as the core of the application. While in this case, we're focusing solely on Compose for Web, by loading this App.kt on each platform, it enables multiplatform support.

With this setup, the basic structure can be implemented.

## Navigation
The next concern that arises is how to handle screen navigation in Compose for Web. While I haven't fully grasped it myself, I'll introduce one approach.

Firstly, it's worth noting that URL-based navigation is challenging in the basic setup. While it's possible to achieve this by preparing multiple HTML files, it would undermine the essence of Compose for Web, which aims to accomplish everything in Kotlin.

So, what's the solution? In my case, I used URL fragment.

```kotlin
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        var path by remember { mutableStateOf(window.location.hash) }
        window.addEventListener("popstate") {
            path = window.location.hash
        }
        App(::navigateTo, path)
    }
}

private fun navigateTo(path: String) {
    if (path.startsWith("#")) {
        window.location.href = "$baseUrl$path"
    } else if (path == "") {
        window.location.href = "$baseUrl/#home"
    } else {
        window.open(path)
    }
}
```
In this example, we read the value attached to the URL fragment and pass it to the App function. On the App side, we receive the destination for screen navigation. Depending on this destination, we load the URL of the corresponding screen to achieve screen navigation.

On the App function side, we switch the Composable function to be rendered based on the string received after the # symbol. This mechanism facilitates switching between different Composable functions for rendering based on the received URL fragment.

## Regarding communication processing
Communication processing can be easily achieved by combining Kotlin's Coroutines with the W3C's fetch API. Below is a simple implementation of a GET request:
```kotlin
window.fetch(path).await<Response>().text().await<JsString>().toString()
```
In this example, we're fetching a simple String from the specified URL.
The W3C's fetch returns values in the form of Promises. By using the await function from Coroutines, we unwrap these Promises to obtain a JsString. We then convert this JsString to a Kotlin String, making it easily usable within Kotlin applications.

## Several things can be easily achieved for Android engineers precisely because of Kotlin.

TBD

## Afterword
- Compose for Web is fundamentally designed with multiplatform in mind. Therefore, it's assumed that development will likely occur simultaneously with Android. However, in Compose for Web, you cannot preview the UI directly, and to preview it, you need to set up an Android project.
- Still in its alpha release, there are many unstable aspects. For example, even for screen navigation, you need to take a workaround approach, indicating that it hasn't established itself as a mature technology yet.
- I also attempted tasks like video rendering, but it required effectively combining web-specific mechanisms. It's not entirely friendly for Android engineers, as they may feel stressed about not being able to utilize the powerful libraries they were accustomed to using in Android.
However, it was undoubtedly an accessible technology for me as an Android engineer. I do have a sense that when it stabilizes, there might be instances where it's introduced in real products. So, I'm looking forward to the future growth of this technology.


