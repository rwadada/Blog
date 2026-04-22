import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    onUrlClick: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHead(label = "Products")

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
        elevation = 0.dp,
        shape = RoundedCornerShape(14.dp),
        backgroundColor = surfaceColor(),
        border = BorderStroke(1.dp, cardBorderColor())
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = accentTextColor()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                color = secondaryTextColor(),
                lineHeight = 21.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Visit Website",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = selectedTextColor()
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = selectedTextColor(),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
