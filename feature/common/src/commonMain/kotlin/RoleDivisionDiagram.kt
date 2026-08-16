import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoleDivisionDiagram() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(surfaceColor())
            .border(1.dp, cardBorderColor(), RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Text(
            text = "人間とAIの最適な責務分離 (Roles Division)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = accentTextColor(),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Step 1: Human (Plan)
        DiagramStep(
            stepNumber = "1",
            actor = "人間 (Human)",
            title = "計画と骨格定義 (Plan & Skeleton)",
            description = "システム全体の設計方針の決定、インターフェースやラフなコード骨格の実装。迷走を防ぐためのガードレールを用意する。",
            actorColor = Color(0xFFFF842A) // borderAccentColor / Orange
        )

        Spacer(modifier = Modifier.height(8.dp))
        ArrowDown()
        Spacer(modifier = Modifier.height(8.dp))

        // Step 2: AI (Meat & Verify)
        DiagramStep(
            stepNumber = "2",
            actor = "AI (Copilot/Agent)",
            title = "詳細実装と自動検証 (Detail & Test)",
            description = "骨格に対する具体的な肉付け、ボイラープレートの生成、テストの自動実行と自己デバッグ。圧倒的な実装速度をフル活用する。",
            actorColor = Color(0xFF4DADF7) // AI blue
        )

        Spacer(modifier = Modifier.height(8.dp))
        ArrowDown()
        Spacer(modifier = Modifier.height(8.dp))

        // Step 3: Human (Review)
        DiagramStep(
            stepNumber = "3",
            actor = "人間 (Human)",
            title = "最終レビュー (Final Review)",
            description = "AIが自動テストを通した成果物に対し、ドメイン要件への適合と設計思想の整合性を最終確認。エッジケースの妥当性を検証する。",
            actorColor = Color(0xFFFF842A) // borderAccentColor / Orange
        )
    }
}

@Composable
private fun DiagramStep(
    stepNumber: String,
    actor: String,
    title: String,
    description: String,
    actorColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor())
            .border(1.dp, cardBorderColor(), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step number indicator
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(actorColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = selectedTextColor()
                )
                Text(
                    text = actor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = actorColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = secondaryTextColor(),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ArrowDown() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "↓",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryTextColor(),
            textAlign = TextAlign.Center
        )
    }
}
