package blog.feature.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement
import org.w3c.dom.Element
import androidx.compose.ui.platform.LocalDensity

private fun setPointerEventsNone(element: Element) {
    js("element.style.pointerEvents = 'none'")
}

@Composable
fun WasmVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    var videoElement by remember { mutableStateOf<HTMLVideoElement?>(null) }
    var bounds by remember { mutableStateOf<Pair<Offset, androidx.compose.ui.unit.IntSize>?>(null) }
    val density = LocalDensity.current.density

    DisposableEffect(videoUrl) {
        val element = document.createElement("video") as HTMLVideoElement
        element.src = videoUrl
        element.autoplay = true
        element.loop = true
        element.muted = true
        element.style.position = "fixed"
        element.style.objectFit = "contain"
        element.style.zIndex = "10" // Make sure it sits above canvas
        setPointerEventsNone(element)
        
        document.body?.appendChild(element)
        videoElement = element

        onDispose {
            element.remove()
        }
    }

    LaunchedEffect(bounds, density) {
        val currentBounds = bounds
        val element = videoElement
        if (currentBounds != null && element != null) {
            val (position, size) = currentBounds
            element.style.left = "${position.x / density}px"
            element.style.top = "${position.y / density}px"
            element.style.width = "${size.width / density}px"
            element.style.height = "${size.height / density}px"
        }
    }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            bounds = Pair(coordinates.positionInWindow(), coordinates.size)
        }
    )
}
