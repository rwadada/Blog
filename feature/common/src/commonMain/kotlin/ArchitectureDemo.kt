import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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

@Composable
fun ArchitectureDemo() {
    var stateVersion by remember { mutableStateOf(1) }
    var runningAction by remember { mutableStateOf<String?>(null) }
    var activeEventLayer by remember { mutableStateOf<Int?>(null) }
    var activeStateLayer by remember { mutableStateOf<Int?>(null) }

    val layers = listOf(
        "Component (User Profile Button)",
        "Section (User Profile Header)",
        "Content (User Profile Content)",
        "Screen (Scaffold & Layout)",
        "Route (Entry Point & DI)",
        "ViewModel (Business Logic & State)"
    )

    LaunchedEffect(runningAction) {
        if (runningAction != null) {
            // Event flows UP
            for (i in 0..5) {
                activeEventLayer = i
                delay(300)
            }
            activeEventLayer = null
            
            // ViewModel updates state
            stateVersion++
            delay(200)

            // State flows DOWN
            for (i in 5 downTo 0) {
                activeStateLayer = i
                delay(300)
            }
            activeStateLayer = null
            runningAction = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(surfaceColor())
            .border(2.dp, borderColor(), RoundedCornerShape(16.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Interactive Architecture Demo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = accentTextColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "State (v$stateVersion) flows down ↓ | Events flow up ↑",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = secondaryTextColor(),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ArchitectureLayer(
            name = layers[5],
            level = 5,
            activeEventLayer = activeEventLayer,
            activeStateLayer = activeStateLayer,
            stateVersion = stateVersion
        ) {
            ArchitectureLayer(
                name = layers[4],
                level = 4,
                activeEventLayer = activeEventLayer,
                activeStateLayer = activeStateLayer,
                stateVersion = stateVersion
            ) {
                ArchitectureLayer(
                    name = layers[3],
                    level = 3,
                    activeEventLayer = activeEventLayer,
                    activeStateLayer = activeStateLayer,
                    stateVersion = stateVersion
                ) {
                    ArchitectureLayer(
                        name = layers[2],
                        level = 2,
                        activeEventLayer = activeEventLayer,
                        activeStateLayer = activeStateLayer,
                        stateVersion = stateVersion
                    ) {
                        ArchitectureLayer(
                            name = layers[1],
                            level = 1,
                            activeEventLayer = activeEventLayer,
                            activeStateLayer = activeStateLayer,
                            stateVersion = stateVersion
                        ) {
                            ArchitectureLayer(
                                name = layers[0],
                                level = 0,
                                activeEventLayer = activeEventLayer,
                                activeStateLayer = activeStateLayer,
                                stateVersion = stateVersion
                            ) {
                                Button(
                                    onClick = {
                                        if (runningAction == null) {
                                            runningAction = "onClick"
                                        }
                                    },
                                    enabled = runningAction == null,
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = borderAccentColor(),
                                        contentColor = backgroundColor()
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = if (runningAction != null) "Processing..." else "Trigger Action (onClick)",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectureLayer(
    name: String,
    level: Int,
    activeEventLayer: Int?,
    activeStateLayer: Int?,
    stateVersion: Int,
    content: @Composable () -> Unit
) {
    val isEventActive = level == activeEventLayer
    val isStateActive = level == activeStateLayer
    
    val bgColor = when {
        isEventActive -> Color(0xFFE57373) // Red-ish for Action Up
        isStateActive -> Color(0xFF64B5F6) // Blue-ish for State Down
        else -> Color.Transparent
    }
    
    val borderColorAnimated = when {
        isEventActive -> Color(0xFFFFCDD2)
        isStateActive -> Color(0xFFBBDEFB)
        else -> borderColor()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(1.dp, borderColorAnimated, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (isStateActive) {
                    Text(
                        text = "State v$stateVersion Down",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isEventActive || isStateActive) Color.White else selectedTextColor(),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )
            
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                if (isEventActive) {
                    Text(
                        text = "Event Up",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        content()
    }
}
