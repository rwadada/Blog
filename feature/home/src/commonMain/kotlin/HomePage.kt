import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(borderAccentColor())
                    )
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .height(4.dp)
                            .background(borderColor())
                    )
                }

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
                Text(
                    text = "ABOUT ME",
                    fontWeight = FontWeight.Bold,
                    color = textColor()
                )
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(borderAccentColor())
                    )
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .height(4.dp)
                            .background(borderColor())
                    )
                }
                Row(Modifier.fillMaxWidth()) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource("profile.jpg"),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().clip(CircleShape),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            text = "Photo by Taisuke Nakamura",
                            fontSize = 8.sp,
                            color = textColor()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(3f)
                    ) {
                        Text(
                            text = "Ryosuke Wada",
                            fontWeight = FontWeight.Bold,
                            color = textColor()
                        )
                        Text(
                            text = "Hello there! I'm an Android engineer.",
                            color = textColor()
                        )
                        Text(
                            text = "I created this website as a space to gather and document my experiences. It's a platform where I share travel logs, book reviews, and delve into Android development techniques. If you're interested, please feel free to explore and discover more.",
                            color = textColor()
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Background",
                            fontWeight = FontWeight.Bold,
                            color = textColor()
                        )
                        Text(
                            text = """
                                1996 Born in Oita Prefecture, Japan
                                2012 Entered Kurume National College of Technology
                                2017 Joined an SIer (System Integration Company) - worked in the finance sector as an Android Engineer
                                2019 Transitioned to a human resources services and advertising/media company as an Android Engineer
                            """.trimIndent(),
                            color = textColor()
                        )
                    }
                }

                Text(
                    text = "SOCIAL",
                    fontWeight = FontWeight.Bold,
                    color = textColor()
                )
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(borderAccentColor())
                    )
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .height(4.dp)
                            .background(borderColor())
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "FACEBOOK",
                            color = textColor()
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "INSTAGRAM",
                            color = textColor()
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "X(TWITTER)",
                            color = textColor()
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "YOUTUBE",
                            color = textColor()
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "GitHub",
                            color = textColor()
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "EMAIL",
                            color = textColor()
                        )
                    }
                }
            }
        }
    }
}
