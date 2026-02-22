import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.ui.unit.sp

@Composable
fun Footer() {
    Row(
        modifier = Modifier
            .background(backgroundColor())
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "© 2026 Ryosuke Wada. Built with Kotlin Wasm.",
            color = secondaryTextColor(),
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}
