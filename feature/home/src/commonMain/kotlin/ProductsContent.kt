import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    onUrlClick: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "PRODUCTS",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = accentTextColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ProductItem(
            name = "Flow",
            description = "A productivity tool that helps you stay focused and manage your tasks efficiently.",
            url = "https://flow.rwadada.com/",
            onClick = onUrlClick
        )
    }
}

@Composable
private fun ProductItem(
    name: String,
    description: String,
    url: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(url) },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = surfaceColor()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = selectedTextColor()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                color = secondaryTextColor(),
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Visit Website ->",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = borderAccentColor()
            )
        }
    }
}
