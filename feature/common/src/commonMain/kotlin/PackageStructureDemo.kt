import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PackageStructureDemo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(surfaceColor())
            .border(1.dp, borderColor(), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Feature: userprofile",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = accentTextColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        FolderItem(name = "Section")
        FileItem(name = "UserProfileHeaderSection.kt", indent = 1)
        FileItem(name = "UserActivityListSection.kt", indent = 1)
        
        FolderItem(name = "Component")
        FileItem(name = "UserProfileTextField.kt", indent = 1)
        
        FileItem(name = "UserProfileRoute.kt", indent = 0)
        FileItem(name = "UserProfileScreen.kt", indent = 0)
        FileItem(name = "UserProfileContent.kt", indent = 0)
    }
}

@Composable
private fun FolderItem(name: String, indent: Int = 0) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = (indent * 24).dp, top = 4.dp, bottom = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFFFCA28)) // Golden folder color
                .padding(end = 6.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = accentTextColor()
        )
    }
}

@Composable
private fun FileItem(name: String, indent: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = (indent * 24 + 12).dp, top = 2.dp, bottom = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(secondaryTextColor())
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            color = selectedTextColor()
        )
    }
}
