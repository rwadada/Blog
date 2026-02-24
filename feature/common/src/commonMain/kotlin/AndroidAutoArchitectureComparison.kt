import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AndroidAutoArchitectureComparison() {
    var isAAOS by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(surfaceColor())
            .border(1.dp, borderColor(), RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Custom Tab Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TabButton(
                text = "Android Auto",
                isSelected = !isAAOS,
                onClick = { isAAOS = false }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TabButton(
                text = "Android Automotive OS",
                isSelected = isAAOS,
                onClick = { isAAOS = true }
            )
        }

        Divider(color = secondaryTextColor().copy(alpha = 0.5f))

        // Visual Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isAAOS) {
                AAOSVisualization()
            } else {
                AndroidAutoVisualization()
            }
        }
    }
}

@Composable
private fun AAOSVisualization() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        // Car Sensors
        ArchitectureNode(
            title = "Car Sensors",
            iconText = "Car",
            color = Color(0xFF00B0FF),
            description = "Sensors & VHAL"
        )

        AnimatedDataFlow(color = Color(0xFF00B0FF), label = "Hardware Data")

        // Car Display (AAOS)
        ArchitectureNode(
            title = "AAOS Display",
            iconText = "Display",
            color = Color(0xFF00E676),
            description = "Runs Android apps locally"
        )
    }
}

@Composable
private fun AndroidAutoVisualization() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        // Car Sensors
        ArchitectureNode(
            title = "Car Sensors",
            iconText = "Car",
            color = Color(0xFF00B0FF),
            description = "Sensors & GPS"
        )

        AnimatedDataFlow(color = Color(0xFF9E9E9E), label = "Sensor Data")

        // Smartphone
        ArchitectureNode(
            title = "Smartphone",
            iconText = "Phone",
            color = Color(0xFFB388FF),
            description = "Runs apps & renders UI"
        )

        AnimatedDataFlow(color = Color(0xFFB388FF), label = "Video Stream")

        // Car Display (AAOS)
        ArchitectureNode(
            title = "Car Display",
            iconText = "Display",
            color = Color(0xFF00E676),
            description = "Dumb monitor"
        )
    }
}

@Composable
private fun ArchitectureNode(title: String, iconText: String, color: Color, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color.copy(alpha = 0.1f))
                .border(2.dp, color, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = iconText, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = accentTextColor())
        Text(text = description, fontSize = 10.sp, color = secondaryTextColor())
    }
}

@Composable
private fun AnimatedDataFlow(color: Color, label: String) {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 10.sp, color = color, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 30.dp, height = 2.dp)
                    .background(color.copy(alpha = 0.3f))
            )
            Text(
                text = ">",
                color = color.copy(alpha = alpha),
                fontSize = 12.sp,
                modifier = Modifier.offset(x = offsetX.dp)
            )
        }
    }
}

@Composable
private fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor = if (isSelected) accentTextColor() else Color.Transparent
    val txtColor = if (isSelected) textColor() else secondaryTextColor()

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = txtColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun Divider(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
    )
}
