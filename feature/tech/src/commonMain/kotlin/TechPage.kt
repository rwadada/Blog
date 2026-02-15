import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import markdown.MarkdownContent
import markdown.MarkdownObject
import usecase.ParseMarkDownStringUseCase
import usecase.ReadFileUseCase

@Composable
fun TechPage(
    index: Int?,
    navigate: (String) -> Unit,
) {
    if (index == null) {
        TechList(navigate)
    } else {
        TechDetail(index, navigate)
    }
}

@Composable
private fun TechList(navigate: (String) -> Unit) {
    val techBlogItems = blogItems.filter { it.type == BlogItem.Type.TECH }
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tech Blog",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 32.dp),
            color = accentTextColor()
        )
        
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            techBlogItems.forEach { item ->
                TechListItem(item) {
                    navigate(item.getDestinationPath())
                }
            }
        }
    }
}

@Composable
private fun TechListItem(item: BlogItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = contentBackgroundColor()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = selectedTextColor(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.date,
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray 
            )
            if (item.summary.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.summary,
                    fontSize = 16.sp,
                    color = textColor(),
                    lineHeight = 24.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun TechDetail(index: Int, navigate: (String) -> Unit) {
    val readFileUseCase = ReadFileUseCase()
    val parseMarkDownStringUseCase = ParseMarkDownStringUseCase()
    var fileContent: List<MarkdownObject> by remember { mutableStateOf(emptyList()) }
    val techBlogItems = blogItems.filter { it.type == BlogItem.Type.TECH }
    
    val safeIndex = if (index in techBlogItems.indices) index else if (techBlogItems.isNotEmpty()) 0 else null
    val item = safeIndex?.let { techBlogItems[it] }

    if (item == null) {
        Text("Article not found", color = accentTextColor())
        return
    }

    LaunchedEffect(item) {
        fileContent = parseMarkDownStringUseCase(readFileUseCase(item.path))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.weight(4f)) {
            MarkdownContent(
                date = item.date,
                content = fileContent,
                modifier = Modifier.fillMaxWidth(),
                composableItem = item.composableItems
            )
            
            // Navigation Buttons
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp)) {
                
                 val newerIndex = safeIndex - 1
                 val olderIndex = safeIndex + 1
                 
                 Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween
                 ) {
                     // Previous (Older)
                     if (olderIndex in techBlogItems.indices) {
                         val prevItem = techBlogItems[olderIndex]
                         NavigationButton(
                             label = "Previous",
                             title = prevItem.title,
                             onClick = { navigate(prevItem.getDestinationPath()) },
                             isPrevious = true,
                             modifier = Modifier.weight(1f).padding(end = 8.dp)
                         )
                     } else {
                         Spacer(Modifier.weight(1f).padding(end = 8.dp))
                     }

                     // Next (Newer)
                     if (newerIndex in techBlogItems.indices) {
                         val nextItem = techBlogItems[newerIndex]
                         NavigationButton(
                             label = "Next",
                             title = nextItem.title,
                             onClick = { navigate(nextItem.getDestinationPath()) },
                             isPrevious = false,
                             modifier = Modifier.weight(1f).padding(start = 8.dp)
                         )
                     } else {
                         Spacer(Modifier.weight(1f).padding(start = 8.dp))
                     }
                 }
                 
                 Spacer(modifier = Modifier.height(32.dp))
                 
                 Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                     Text(
                         text = "Back to List",
                         modifier = Modifier
                             .clickable { navigate(Tech.path) }
                             .padding(16.dp),
                         color = accentTextColor(),
                         fontWeight = FontWeight.Bold,
                         fontSize = 16.sp
                     )
                 }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun NavigationButton(
    label: String,
    title: String,
    onClick: () -> Unit,
    isPrevious: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
         modifier = modifier.clickable(onClick = onClick),
         elevation = 2.dp,
         shape = RoundedCornerShape(8.dp),
         backgroundColor = codeBlockBackgroundColor() 
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = if (isPrevious) Alignment.Start else Alignment.End
        ) {
            Text(
                text = if (isPrevious) "< $label" else "$label >",
                fontSize = 12.sp,
                color = secondaryTextColor(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = accentTextColor(),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = if (isPrevious) TextAlign.Start else TextAlign.End
            )
        }
    }
}
