import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import blog.feature.common.generated.resources.Res
import blog.feature.common.generated.resources.search

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SearchPage(
    navigate: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Filter the items based on the search query. Ignore case.
    val filteredItems = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            blogItems.filter { item ->
                item.title.contains(searchQuery, ignoreCase = true) || 
                item.summary.contains(searchQuery, ignoreCase = true)
            }.reversed() // Show newest first
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            placeholder = { 
                Text(
                    text = "Search by title or summary...",
                    color = secondaryTextColor().copy(alpha = 0.5f)
                ) 
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.search),
                    contentDescription = "Search Icon",
                    tint = secondaryTextColor()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = accentTextColor(),
                backgroundColor = surfaceColor(),
                cursorColor = accentTextColor(),
                focusedBorderColor = accentTextColor(),
                unfocusedBorderColor = borderColor()
            ),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        // Results or Empty State
        if (searchQuery.isNotBlank() && filteredItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No results found.",
                    color = secondaryTextColor(),
                    fontSize = 18.sp
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                filteredItems.forEach { item ->
                    SearchResultItem(item = item) {
                        navigate(item.getDestinationPath())
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(item: BlogItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp,
        shape = RoundedCornerShape(24.dp),
        backgroundColor = surfaceColor()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Tag to show which category this result belongs to
            Text(
                text = item.type.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = selectedTextColor(),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = item.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = accentTextColor(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.date,
                fontSize = 14.sp,
                color = secondaryTextColor()
            )
            if (item.summary.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.summary,
                    fontSize = 16.sp,
                    color = secondaryTextColor(),
                    lineHeight = 24.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
