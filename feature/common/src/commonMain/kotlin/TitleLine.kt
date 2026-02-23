import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleLine() {
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600)
        )
    }

    Row(Modifier.fillMaxWidth(animationProgress.value)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .background(borderAccentColor())
        )
        Box(
            modifier = Modifier
                .weight(5f)
                .height(4.dp)
                .background(borderColor())
        )
    }
}
