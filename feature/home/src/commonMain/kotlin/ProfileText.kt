import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ProfileText(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Ryosuke Wada",
            fontWeight = FontWeight.Bold,
            color = textColor()
        )
        Text(
            text = "I'm an Android engineer.",
            color = textColor()
        )
        Text(
            text = "I created this website as a space to gather and document my experiences. It's a platform where I share travel logs, book reviews, and delve into Android development techniques. If you're interested, please feel free to explore and discover more.",
            color = textColor()
        )
    }
}
