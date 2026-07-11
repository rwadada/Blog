import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// material-icons is no longer published for current Kotlin/Wasm, so the few
// navigation glyphs the blog uses are inlined here with the same path data.

private fun navigationIcon(
    name: String,
    pathBuilder: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color.Black)) { pathBuilder() }
}.build()

val KeyboardArrowRightIcon: ImageVector by lazy {
    navigationIcon("KeyboardArrowRight") {
        moveTo(8.59f, 16.59f)
        lineTo(13.17f, 12f)
        lineTo(8.59f, 7.41f)
        lineTo(10f, 6f)
        lineToRelative(6f, 6f)
        lineToRelative(-6f, 6f)
        close()
    }
}

val KeyboardArrowLeftIcon: ImageVector by lazy {
    navigationIcon("KeyboardArrowLeft") {
        moveTo(15.41f, 16.59f)
        lineTo(10.83f, 12f)
        lineToRelative(4.58f, -4.59f)
        lineTo(14f, 6f)
        lineToRelative(-6f, 6f)
        lineToRelative(6f, 6f)
        close()
    }
}

val ArrowBackIcon: ImageVector by lazy {
    navigationIcon("ArrowBack") {
        moveTo(20f, 11f)
        horizontalLineTo(7.83f)
        lineToRelative(5.59f, -5.59f)
        lineTo(12f, 4f)
        lineToRelative(-8f, 8f)
        lineToRelative(8f, 8f)
        lineToRelative(1.41f, -1.41f)
        lineTo(7.83f, 13f)
        horizontalLineTo(20f)
        verticalLineToRelative(-2f)
        close()
    }
}
