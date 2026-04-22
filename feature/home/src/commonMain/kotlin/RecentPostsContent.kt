import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentPostsContent(
    modifier: Modifier = Modifier,
    navigateBlogItem: (BlogItem) -> Unit
) {
    val recentPosts = remember { blogItems.reversed().take(3) }
    val featured = recentPosts.first()
    val rest = recentPosts.drop(1)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHead(label = "Recent Posts")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeaturedCard(
                item = featured,
                modifier = Modifier.weight(1f),
                onClick = navigateBlogItem
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rest.forEach { item ->
                    SmallCard(item = item, onClick = navigateBlogItem)
                }
            }
        }
    }
}

@Composable
private fun FeaturedCard(item: BlogItem, modifier: Modifier, onClick: (BlogItem) -> Unit) {
    Card(
        modifier = modifier.clickable { onClick(item) },
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = surfaceColor(),
        border = BorderStroke(1.dp, cardBorderColor())
    ) {
        Column {
            TypeStrip(type = item.type, height = 5.dp)
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.type.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = item.type.stripColor,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.1f, androidx.compose.ui.unit.TextUnitType.Em)
                    )
                    Text(
                        text = item.date,
                        fontSize = 11.sp,
                        color = secondaryTextColor().copy(alpha = 0.6f)
                    )
                }
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = accentTextColor(),
                    lineHeight = 24.sp
                )
                if (item.summary.isNotEmpty()) {
                    Text(
                        text = item.summary,
                        fontSize = 13.sp,
                        color = secondaryTextColor(),
                        lineHeight = 21.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "READ MORE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = selectedTextColor()
                    )
                }
            }
        }
    }
}

@Composable
private fun SmallCard(item: BlogItem, onClick: (BlogItem) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(item) },
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = surfaceColor(),
        border = BorderStroke(1.dp, cardBorderColor())
    ) {
        Column {
            TypeStrip(type = item.type, height = 3.dp)
            Column(
                modifier = Modifier.padding(14.dp, 14.dp, 14.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.type.name,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = item.type.stripColor,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.1f, androidx.compose.ui.unit.TextUnitType.Em)
                    )
                    Text(
                        text = item.date,
                        fontSize = 10.sp,
                        color = secondaryTextColor().copy(alpha = 0.6f)
                    )
                }
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentTextColor(),
                    lineHeight = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (item.summary.isNotEmpty()) {
                    Text(
                        text = item.summary,
                        fontSize = 12.sp,
                        color = secondaryTextColor(),
                        lineHeight = 19.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
