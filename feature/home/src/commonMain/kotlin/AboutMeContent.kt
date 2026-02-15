import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AboutMeContent(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "ABOUT ME",
            fontWeight = FontWeight.Bold,
            color = accentTextColor()
        )
        TitleLine()
        Row(Modifier.fillMaxWidth()) {
            ProfilePhoto(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3f)
            ) {
                ProfileText(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                BackgroundText(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
