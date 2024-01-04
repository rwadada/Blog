import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundText(modifier: Modifier) {
    val backgrounds = listOf(
        Background(
            year = "1996",
            description = "Born in Oita Prefecture, Japan"
        ),
        Background(
            year = "2012",
            description = "Entered Kurume National College of Technology"
        ),
        Background(
            year = "2017",
            description = "Joined an SIer (System Integration Company) - worked in the finance sector as an Android Engineer"
        ),
        Background(
            year = "2019",
            description = "Transitioned to a human resources services and advertising/media company as an Android Engineer"
        ),
    )

    Column(modifier) {
        Text(
            text = "Background",
            fontWeight = FontWeight.Bold,
            color = textColor()
        )
        backgrounds.forEach { background ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = background.year,
                    color = textColor()
                )
                Text(
                    text = background.description,
                    color = textColor(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private data class Background(
    val year: String,
    val description: String,
)
