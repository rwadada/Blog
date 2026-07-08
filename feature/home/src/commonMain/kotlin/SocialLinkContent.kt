import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import blog.feature.home.generated.resources.Res
import blog.feature.home.generated.resources.instagram
import blog.feature.home.generated.resources.linkedin
import blog.feature.home.generated.resources.github
import blog.feature.home.generated.resources.email

enum class SocialLink(val url: String) {
    GITHUB("https://github.com/rwadada"),
    EMAIL("mailto:wadada0420@gmail.com"),
    INSTAGRAM("https://www.instagram.com/ryosuke.wada.925"),
    LINKEDIN("https://www.linkedin.com/in/rwadada0420"),
}

@Composable
fun SocialLinkContent(
    modifier: Modifier,
    onUrlClick: (String) -> Unit
) {
    Column(modifier) {
        SectionHead(label = "Connect")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialLinkButton(
                link = SocialLink.GITHUB,
                onUrlClick = onUrlClick,
                modifier = Modifier.weight(1f)
            )
            SocialLinkButton(
                link = SocialLink.EMAIL,
                onUrlClick = onUrlClick,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialLinkButton(
                link = SocialLink.LINKEDIN,
                onUrlClick = onUrlClick,
                modifier = Modifier.weight(1f)
            )
            SocialLinkButton(
                link = SocialLink.INSTAGRAM,
                onUrlClick = onUrlClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SocialLinkButton(
    link: SocialLink,
    onUrlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(44.dp)
            .clickable {
                onUrlClick(link.url)
            }
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(link.displayIcon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            colorFilter = ColorFilter.tint(accentTextColor())
        )
        Text(
            text = link.displayText,
            color = accentTextColor(),
            fontWeight = FontWeight.Bold
        )
    }
}

private val SocialLink.displayText: String
    get() = when (this) {
        SocialLink.INSTAGRAM -> "Instagram"
        SocialLink.GITHUB -> "GitHub"
        SocialLink.EMAIL -> "Email"
        SocialLink.LINKEDIN -> "LinkedIn"
    }

@OptIn(ExperimentalResourceApi::class)
private val SocialLink.displayIcon: DrawableResource
    get() = when (this) {
        SocialLink.INSTAGRAM -> Res.drawable.instagram
        SocialLink.GITHUB -> Res.drawable.github
        SocialLink.EMAIL -> Res.drawable.email
        SocialLink.LINKEDIN -> Res.drawable.linkedin
    }
