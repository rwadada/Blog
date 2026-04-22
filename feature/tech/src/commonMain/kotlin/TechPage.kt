import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val techBlogItems = remember { blogItems.filter { it.type == BlogItem.Type.TECH }.reversed() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor())
            .padding(28.dp)
    ) {
        SectionHead(label = "Tech")
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            techBlogItems.forEach { item ->
                TechListCard(item) { navigate(item.getDestinationPath()) }
            }
        }
    }
}

@Composable
private fun TechListCard(item: BlogItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = surfaceColor(),
        border = BorderStroke(1.dp, cardBorderColor())
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(item.type.stripColor)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = item.date,
                    fontSize = 11.sp,
                    color = secondaryTextColor().copy(alpha = 0.6f)
                )
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentTextColor(),
                    lineHeight = 21.sp
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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = secondaryTextColor().copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 12.dp).size(20.dp).align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun TechDetail(index: Int, navigate: (String) -> Unit) {
    val readFileUseCase = ReadFileUseCase()
    var fileContent by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val techBlogItems = remember { blogItems.filter { it.type == BlogItem.Type.TECH } }

    val safeIndex = if (index in techBlogItems.indices) index else if (techBlogItems.isNotEmpty()) 0 else null
    val item = safeIndex?.let { techBlogItems[it] }

    if (item == null) {
        Text("Article not found", color = accentTextColor())
        return
    }

    LaunchedEffect(item.path) {
        fileContent = ""
        errorMessage = null
        try {
            val content = readFileUseCase(item.path)
            fileContent = content
        } catch (e: Exception) {
            errorMessage = "Failed to load article: ${e.message}"
            e.printStackTrace()
        }
    }

    Column(modifier = Modifier.fillMaxWidth().background(backgroundColor())) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(vertical = 32.dp)
            ) {
                // Back button
                Row(
                    modifier = Modifier
                        .clickable { navigate(Tech.path) }
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = selectedTextColor(),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "TECH",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = selectedTextColor(),
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.05f, androidx.compose.ui.unit.TextUnitType.Em)
                    )
                }

                // Type label
                Text(
                    text = item.type.name,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = item.type.stripColor,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(0.12f, androidx.compose.ui.unit.TextUnitType.Em),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    MarkdownContent(
                        date = item.date,
                        content = fileContent,
                        modifier = Modifier.fillMaxWidth(),
                        composableItem = item.composableItems
                    )
                }

                // Navigation
                Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)) {
                    val newerIndex = safeIndex + 1
                    val olderIndex = safeIndex - 1

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (olderIndex in techBlogItems.indices) {
                            val prevItem = techBlogItems[olderIndex]
                            NavigationButton(
                                label = "Older Post",
                                title = prevItem.title,
                                onClick = { navigate(prevItem.getDestinationPath()) },
                                isLeft = true,
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                        } else {
                            Spacer(Modifier.weight(1f))
                        }

                        if (newerIndex in techBlogItems.indices) {
                            val nextItem = techBlogItems[newerIndex]
                            NavigationButton(
                                label = "Newer Post",
                                title = nextItem.title,
                                onClick = { navigate(nextItem.getDestinationPath()) },
                                isLeft = false,
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            )
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun NavigationButton(
    label: String,
    title: String,
    onClick: () -> Unit,
    isLeft: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = surfaceColor(),
        border = BorderStroke(1.dp, cardBorderColor())
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = if (isLeft) Alignment.Start else Alignment.End
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (isLeft) Arrangement.Start else Arrangement.End
            ) {
                if (isLeft) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = secondaryTextColor(),
                        modifier = Modifier.size(14.dp)
                    )
                }
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = secondaryTextColor(),
                    fontWeight = FontWeight.Bold
                )
                if (!isLeft) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = secondaryTextColor(),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                color = accentTextColor(),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = if (isLeft) TextAlign.Start else TextAlign.End
            )
        }
    }
}
