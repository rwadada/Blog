import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import blog.feature.common.generated.resources.Res
import blog.feature.common.generated.resources.logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComingSoonPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor()),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
            backgroundColor = surfaceColor(),
            border = BorderStroke(1.dp, cardBorderColor())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    tint = selectedTextColor(),
                    modifier = Modifier.height(48.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "COMING SOON",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = selectedTextColor(),
                    letterSpacing = androidx.compose.ui.unit.TextUnit(0.14f, androidx.compose.ui.unit.TextUnitType.Em)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Coming soon",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentTextColor()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "このセクションは現在準備中です。",
                    fontSize = 13.sp,
                    color = secondaryTextColor()
                )
            }
        }
    }
}
