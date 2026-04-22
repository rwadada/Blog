import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TitleLine() {
    Box(
        modifier = Modifier
            .padding(top = 6.dp, bottom = 18.dp)
            .width(36.dp)
            .height(3.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(borderAccentColor())
    )
}
