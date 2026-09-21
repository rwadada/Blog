import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private data class RuleAction(
    val label: String,
    val color: Color,
    val description: String,
    val result: String
)

private val ruleActions = listOf(
    RuleAction(
        label = "mock",
        color = Color(0xFF64B5F6),
        description = "実サーバーに問い合わせず即レスポンス",
        result = "200 OK { \"id\": 1, \"name\": \"Mock User\" }"
    ),
    RuleAction(
        label = "route",
        color = Color(0xFF81C784),
        description = "宛先ホスト/ポートを差し替えて中継",
        result = "staging.api -> localhost:3000 へリダイレクト"
    ),
    RuleAction(
        label = "rewrite",
        color = Color(0xFFFFB74D),
        description = "ヘッダー/ボディを書き換えて中継",
        result = "Authorization ヘッダーを固定値に差し替え"
    ),
    RuleAction(
        label = "breakpoint",
        color = Color(0xFFE57373),
        description = "その場で一時停止し、手動で編集して再開",
        result = "ダッシュボード上で body を編集 -> Resume"
    ),
    RuleAction(
        label = "マッチなし",
        color = Color(0xFF9E9E9E),
        description = "どのルールにもマッチせず素通り",
        result = "実サーバーへそのまま中継"
    )
)

@Composable
fun RuleEngineDemo() {
    var stepIndex by remember { mutableStateOf(0) } // 0: idle/request, 1: matching, 2: action, 3: result
    var actionIndex by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            stepIndex = 1
            delay(500)
            stepIndex = 2
            delay(600)
            stepIndex = 3
            delay(900)
            stepIndex = 0
            isRunning = false
        }
    }

    val currentAction = ruleActions[actionIndex]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(surfaceColor())
            .border(1.dp, cardBorderColor(), RoundedCornerShape(16.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rule Engine の動き",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = accentTextColor(),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "リクエストが rules.json のルールにマッチし、アクションが実行されるまでの流れ",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = secondaryTextColor(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Pipeline
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PipelineStage(
                title = "1. Client Request",
                subtitle = "GET https://api.example.com/users/1",
                active = stepIndex == 1,
                activeColor = borderAccentColor()
            )
            PipelineArrow()
            PipelineStage(
                title = "2. Rule Engine - 上から順にマッチ判定",
                subtitle = "match: { method, url } を rules.json 内で順に評価",
                active = stepIndex == 1,
                activeColor = borderAccentColor()
            )
            PipelineArrow()
            PipelineStage(
                title = "3. Action: ${currentAction.label}",
                subtitle = currentAction.description,
                active = stepIndex == 2,
                activeColor = currentAction.color
            )
            PipelineArrow()
            PipelineStage(
                title = "4. Result",
                subtitle = if (stepIndex == 3) currentAction.result else "-",
                active = stepIndex == 3,
                activeColor = currentAction.color
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (!isRunning) {
                        actionIndex = (actionIndex + 1) % ruleActions.size
                        isRunning = true
                    }
                },
                enabled = !isRunning,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = borderAccentColor(),
                    contentColor = backgroundColor()
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (isRunning) "処理中..." else "リクエストを送る",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PipelineStage(
    title: String,
    subtitle: String,
    active: Boolean,
    activeColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (active) activeColor.copy(alpha = 0.18f) else Color.Transparent)
            .border(
                width = if (active) 2.dp else 1.dp,
                color = if (active) activeColor else borderColor().copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (active) activeColor else selectedTextColor()
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = secondaryTextColor(),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun PipelineArrow() {
    Text(
        text = "|",
        fontSize = 18.sp,
        color = secondaryTextColor(),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
