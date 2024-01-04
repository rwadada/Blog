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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialLinkContent(
    modifier: Modifier,
    navigate: (Home.HomeDestination) -> Unit
) {
    Column(modifier) {
        Text(
            text = "SOCIAL",
            fontWeight = FontWeight.Bold,
            color = textColor()
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
        Image(
            painter = painterResource(destination.displayIcon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = destination.displayText,
            color = textColor(),
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

private val Home.HomeDestination.displayIcon: String
    get() = when(this) {
        Home.HomeDestination.HOME -> ""
        Home.HomeDestination.FACEBOOK -> "logo/facebook.xml"
        Home.HomeDestination.INSTAGRAM -> "logo/instagram.xml"
        Home.HomeDestination.TWITTER -> "logo/twitter.xml"
        Home.HomeDestination.YOUTUBE -> "logo/youtube.xml"
        Home.HomeDestination.GITHUB -> "logo/github.xml"
        Home.HomeDestination.EMAIL -> "logo/email.xml"
    }
