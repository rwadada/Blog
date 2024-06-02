import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class BlogItem(
    val path: String,
    val title: String,
    val date: String,
    val type: Type,
    val summary: String = "",
    val composableItems: List<@Composable () -> Unit> = emptyList()
) {
    enum class Type {
        TECH,
        TRAVEL,
        BOOKS,
    }
}

val blogItems = listOf(
    BlogItem(
        path = "article/tech/AboutKotlinWasm.md",
        title = "About Kotlin Wasm",
        date = "2024-02-19",
        type = BlogItem.Type.TECH,
        summary = "Kotlin/Wasm, a new alternative to Kotlin/JS, shows promising performance for web applications using Kotlin Multiplatform. With its integration in Compose for Web, developers can build web UIs similarly to Android UIs using SKIA for rendering. Despite being in alpha, it offers easy setup for Kotlin-based web projects, though some features, like screen navigation, require workarounds.",
        composableItems = listOf {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LoadingDots()
            }
        }
    ),
    BlogItem(
        path = "article/tech/QualityInSoftwareDevelopment-MyValuesAndApproach.md",
        title = "Quality in Software Development: My Values and Approach",
        date = "2024-06-02",
        type = BlogItem.Type.TECH,
        summary = "Software quality involves both external factors like reliability and usability, and internal factors such as code readability and reduced complexity. Improving quality through refactoring and adopting new technologies is effective, but it's essential to define clear goals and ensure team cohesion."
    )
)

fun BlogItem.getDestinationPath(): String {
    val destination = when (type) {
        BlogItem.Type.TECH -> Tech
        BlogItem.Type.TRAVEL -> Travel
        BlogItem.Type.BOOKS -> Books
    }
    val index = blogItems.filter { it.type == type }.indexOf(this)

    return destination.path + "/$index"
}
