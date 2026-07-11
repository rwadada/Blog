import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalBrowserHistoryApi
fun main() {
    ComposeViewport(viewportContainerId = "ComposeTarget") {
        App(
            onOpenUrl = { window.open(it) },
            onTitleChange = { document.title = it },
            onNavHostReady = { it.bindToBrowserNavigation() }
        )
    }
}
