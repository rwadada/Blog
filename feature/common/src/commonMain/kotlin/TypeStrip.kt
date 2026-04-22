import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val BlogItem.Type.stripColor: Color
    get() = when (this) {
        BlogItem.Type.TECH -> Color(0xFFFF842A)
        BlogItem.Type.TRAVEL -> Color(0xFF4A9EFF)
        BlogItem.Type.BOOKS -> Color(0xFFA78BFA)
    }

@Composable
fun TypeStrip(type: BlogItem.Type, height: Dp = 4.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(type.stripColor)
    )
}
