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
