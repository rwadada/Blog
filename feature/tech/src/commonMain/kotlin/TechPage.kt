import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import markdown.MarkdownContent
import markdown.MarkdownObject
import usecase.ParseMarkDownStringUseCase
import usecase.ReadFileUseCase


@Composable
fun TechPage() {
    val readFileUseCase = ReadFileUseCase()
    val parseMarkDownStringUseCase = ParseMarkDownStringUseCase()
    var fileContent: List<MarkdownObject> by remember { mutableStateOf(emptyList()) }
    LaunchedEffect(Unit) {
        fileContent = parseMarkDownStringUseCase(readFileUseCase("article/tech/AboutKotlinWasm.md"))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        MarkdownContent(
            content = fileContent,
            modifier = Modifier.weight(4f),
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LoadingDots()
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LoadingDots() {

    @Composable
    fun Dot(
        scale: Float,
        color: Color
    ) = Spacer(
        Modifier
            .size(24.dp)
            .scale(scale)
            .background(
                color = color,
                shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 300 * 4
                0f at delay with LinearEasing
                1f at delay + 300 with LinearEasing
                0f at delay + 300 * 2
            }
        )
    )

    val scale1 by animateScaleWithDelay(0)
    val scale2 by animateScaleWithDelay(300)
    val scale3 by animateScaleWithDelay(300 * 2)

    @Composable
    fun animationColorWithDelay(delay: Int) = infiniteTransition.animateColor(
        initialValue = backgroundColor(),
        targetValue = borderAccentColor(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 300 * 4
                Color(0xFF333333) at delay with LinearEasing
                Color(0xFFFF842A) at delay + 300 with LinearEasing
                Color(0xFF333333) at delay + 300 * 2
            }
        )
    )

    val color1 by animationColorWithDelay(0)
    val color2 by animationColorWithDelay(300)
    val color3 by animationColorWithDelay(300 * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp

        Dot(scale1, color1)
        Spacer(Modifier.width(spaceSize))
        Dot(scale2, color2)
        Spacer(Modifier.width(spaceSize))
        Dot(scale3, color3)
    }
}
