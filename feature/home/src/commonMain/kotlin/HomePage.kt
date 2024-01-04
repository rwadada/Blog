import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomePage(navigate: (Home.HomeDestination) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(contentBackgroundColor())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(320.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource("header.jpg"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "Pages, Places, and Programming: Exploring the World through Books, Nature, and Android",
                color = accentTextColor(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                SelectionContainer {
                    Text(
                        text = "RECENT POSTS",
                        fontWeight = FontWeight.Bold,
                        color = textColor()
                    )
                }
                TitleLine()

                for (i in 0..4) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No items",
                            color = textColor()
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, borderColor())
                    .padding(8.dp)
            ) {
                AboutMeContent(modifier = Modifier.fillMaxWidth())
                SocialLinkContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    navigate = navigate
                )
            }
        }
    }
}
