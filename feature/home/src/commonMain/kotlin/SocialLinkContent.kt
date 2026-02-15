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
import blog.feature.home.generated.resources.facebook
import blog.feature.home.generated.resources.instagram
import blog.feature.home.generated.resources.twitter
import blog.feature.home.generated.resources.youtube
import blog.feature.home.generated.resources.github
import blog.feature.home.generated.resources.email

@Composable
fun SocialLinkContent(
    modifier: Modifier,
    navigate: (Home.HomeDestination) -> Unit
) {
    Column(modifier) {
        Text(
            text = "SOCIAL",
            fontWeight = FontWeight.Bold,
            color = accentTextColor()
        )
        TitleLine()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialLinkButton(
                destination = Home.HomeDestination.FACEBOOK,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
            SocialLinkButton(
                destination = Home.HomeDestination.INSTAGRAM,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialLinkButton(
                destination = Home.HomeDestination.TWITTER,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
            SocialLinkButton(
                destination = Home.HomeDestination.YOUTUBE,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialLinkButton(
                destination = Home.HomeDestination.GITHUB,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
            SocialLinkButton(
                destination = Home.HomeDestination.EMAIL,
                navigate = navigate,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SocialLinkButton(
    destination: Home.HomeDestination,
    navigate: (Home.HomeDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(44.dp)
            .clickable {
                navigate(destination)
            }
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val icon = destination.displayIcon
        if (icon != null) {
            val colorFilter = when (destination) {
                Home.HomeDestination.TWITTER,
                Home.HomeDestination.GITHUB,
                Home.HomeDestination.EMAIL -> ColorFilter.tint(accentTextColor())
                else -> null
            }
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                colorFilter = colorFilter
            )
        }
        Text(
            text = destination.displayText,
            color = accentTextColor(),
            fontWeight = FontWeight.Bold
        )
    }
}

private val Home.HomeDestination.displayText: String
    get() = when (this) {
        Home.HomeDestination.HOME -> ""
        Home.HomeDestination.FACEBOOK -> "FACEBOOK"
        Home.HomeDestination.INSTAGRAM -> "INSTAGRAM"
        Home.HomeDestination.TWITTER -> "X(TWITTER)"
        Home.HomeDestination.YOUTUBE -> "YOUTUBE"
        Home.HomeDestination.GITHUB -> "GITHUB"
        Home.HomeDestination.EMAIL -> "EMAIL"
    }

@OptIn(ExperimentalResourceApi::class)
private val Home.HomeDestination.displayIcon: DrawableResource?
    get() = when(this) {
        Home.HomeDestination.HOME -> null
        Home.HomeDestination.FACEBOOK -> Res.drawable.facebook
        Home.HomeDestination.INSTAGRAM -> Res.drawable.instagram
        Home.HomeDestination.TWITTER -> Res.drawable.twitter
        Home.HomeDestination.YOUTUBE -> Res.drawable.youtube
        Home.HomeDestination.GITHUB -> Res.drawable.github
        Home.HomeDestination.EMAIL -> Res.drawable.email
    }
