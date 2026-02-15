package markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import accentTextColor
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownListItem(item: MarkdownObject.ListItem) {
    when (item) {
        is MarkdownObject.ListItem.DotList-> {
            DotListItem(item)
        }
        is MarkdownObject.ListItem.OrderedList -> {
            OrderListItem(item)
        }
    }
}

@Composable
private fun DotListItem(item: MarkdownObject.ListItem.DotList) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        val space = 8 * item.level
        Spacer(modifier = Modifier.width(space.dp))
        Text(
            text = "•  ",
            color = accentTextColor(),
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        Text(
            text = item.text,
            color = accentTextColor(),
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun OrderListItem(item: MarkdownObject.ListItem.OrderedList) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        val space = 8 * item.level
        Spacer(modifier = Modifier.width(space.dp))
        val index = item.text.take(3)
        val text = item.text.drop(3)
        Text(
            text = index,
            color = accentTextColor(),
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        Text(
            text = text,
            color = accentTextColor(),
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}
