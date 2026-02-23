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

@Composable
fun WasmVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    var videoElement by remember { mutableStateOf<HTMLVideoElement?>(null) }
    var bounds by remember { mutableStateOf<Pair<Offset, androidx.compose.ui.unit.IntSize>?>(null) }

    DisposableEffect(videoUrl) {
        val element = document.createElement("video") as HTMLVideoElement
        element.src = videoUrl
        element.autoplay = true
        element.loop = true
        element.muted = true
        element.style.position = "absolute"
        element.style.objectFit = "cover"
        element.style.zIndex = "10" // Make sure it sits above canvas
        
        document.body?.appendChild(element)
        videoElement = element

        onDispose {
            element.remove()
        }
    }

    LaunchedEffect(bounds) {
        val currentBounds = bounds
        val element = videoElement
        if (currentBounds != null && element != null) {
            val (position, size) = currentBounds
            element.style.left = "${position.x}px"
            element.style.top = "${position.y}px"
            element.style.width = "${size.width}px"
            element.style.height = "${size.height}px"
        }
    }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            bounds = Pair(coordinates.positionInWindow(), coordinates.size)
        }
    )
}
