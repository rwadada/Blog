import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class BlogItem(
    val path: String,
    val title: String,
    val date: String,
    val type: Type,
    val composableItems: List<@Composable () -> Unit>
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
        path = "article/tech/Sample.md",
        title = "Sample",
        date = "2024-02-30",
        type = BlogItem.Type.TECH,
        composableItems = listOf {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LoadingDots()
            }
        }
    ),
)
