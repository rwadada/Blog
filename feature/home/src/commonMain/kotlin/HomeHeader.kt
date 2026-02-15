import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import blog.feature.home.generated.resources.Res
import blog.feature.home.generated.resources.header

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeHeader() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(500.dp), // Increased height for Hero effect
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.header),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop // Crop to fill appropriately
        )

        // Dark Gradient Overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ryosuke Wada",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.8f),
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Exploring the World through Books, Nature, and Android",
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.8f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
