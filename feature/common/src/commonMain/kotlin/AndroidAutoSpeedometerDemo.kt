import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AndroidAutoSpeedometerDemo() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 押されている間は徐々に加速（目標120km/h）、離すと減速（目標0km/h）
    val targetSpeed = if (isPressed) 120f else 0f

    // 加速は6秒かけてゆっくり、減速は3秒かけて少し早めにする
    val animationDuration = if (isPressed) 6000 else 3000

    val currentSpeed by animateFloatAsState(
        targetValue = targetSpeed,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearOutSlowInEasing)
    )

    // Android Auto (PaneTemplate風) のデザインを模倣した黒基調のUI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1E1E1E)) // 車載器らしい深いブラック
            .border(2.dp, Color.DarkGray, RoundedCornerShape(16.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(accentTextColor())
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "AndroidAuto デモアプリ",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.DarkGray)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // スピード表示部分
        Text(
            text = "現在の車速",
            color = Color.LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = ((currentSpeed * 10).toInt() / 10f).toString(),
                color = Color.White,
                fontSize = 64.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " km/h",
                color = Color.LightGray,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // アクセルボタン
        Button(
            onClick = { /* interactionSource で押下状態を取るので空でOK */ },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isPressed) Color(0xFFFF5252) else accentTextColor(),
                contentColor = if (isPressed) Color.White else textColor()
            ),
            shape = CircleShape,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(
                text = if (isPressed) "Accelerating..." else "Hold to Accelerate",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
