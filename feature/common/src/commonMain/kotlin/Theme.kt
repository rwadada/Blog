import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import blog.feature.common.generated.resources.*

val NotoSansJp: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.noto_sans_jp_regular, FontWeight.Normal),
        Font(Res.font.noto_sans_jp_bold, FontWeight.Bold),
        Font(Res.font.noto_sans_jp_black, FontWeight.Black)
    )

val AppTypography: Typography
    @Composable
    get() = Typography(defaultFontFamily = NotoSansJp)
