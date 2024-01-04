import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        var path by remember { mutableStateOf(window.location.hash) }
        window.addEventListener("popstate") {
            // URLが変更された時の処理をここに記述
            path = window.location.hash
        }
        App(::navigateTo, path)
    }
}

private fun navigateTo(path: String) {
    if (path.startsWith("#")) {
        window.location.href = "$baseUrl$path"
        document.title = "Ryosuke Wada" + when (path) {
            "#/home" -> ""
            "#/tech" -> " - Tech"
            "#/travel" -> " - Travel"
            "#/books" -> " - Books"
            "#/photo" -> " - Photo"
            "#/contact" -> " - Contact"
            "#/search" -> " - Search"
            else -> ""
        }
    } else if (path == "") {
        window.location.href = "$baseUrl/#home"
    } else {
        window.open(path)
    }
}

private val baseUrl = window.location.origin
