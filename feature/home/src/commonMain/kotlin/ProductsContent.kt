import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val RWADADA_WORKS_URL = "https://rwadada.com/"

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    onUrlClick: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHead(label = "RWADADA WORKS")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onUrlClick(RWADADA_WORKS_URL) },
            elevation = 0.dp,
            shape = RoundedCornerShape(14.dp),
            backgroundColor = surfaceColor(),
            border = BorderStroke(1.dp, cardBorderColor())
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = "Mobile App Engineering & Independent Products",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = selectedTextColor(),
                    letterSpacing = TextUnit(0.06f, TextUnitType.Em)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "RWADADA WORKS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentTextColor()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "モバイルアプリ（iOS / Android）の業務委託開発および、Tripolio・flow・Detour などのオリジナルプロダクトの企画・開発・運営を行っています。事業内容やプロダクト一覧、お仕事のご相談は公式サイトをご覧ください。",
                    fontSize = 13.sp,
                    color = secondaryTextColor(),
                    lineHeight = 21.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Visit rwadada.com",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = selectedTextColor()
                    )
                    Icon(
                        imageVector = KeyboardArrowRightIcon,
                        contentDescription = null,
                        tint = selectedTextColor(),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
