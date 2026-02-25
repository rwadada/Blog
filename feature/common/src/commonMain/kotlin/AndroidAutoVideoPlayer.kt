import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import blog.feature.common.VideoPlayer

@Composable
fun AndroidAutoVideoPlayer(videoUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .border(2.dp, borderColor(), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        VideoPlayer(
            videoUrl = videoUrl,
            modifier = Modifier.fillMaxSize()
        )
    }
}
