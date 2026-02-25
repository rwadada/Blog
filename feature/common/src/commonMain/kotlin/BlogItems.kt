import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class BlogItem(
    val path: String,
    val title: String,
    val date: String,
    val type: Type,
    val summary: String = "",
    val composableItems: List<@Composable () -> Unit> = emptyList()
) {
    enum class Type {
        TECH,
        TRAVEL,
        BOOKS,
    }
}

val blogItems = listOf(
    BlogItem(
        path = "article/tech/AboutKotlinWasm.md",
        title = "About Kotlin Wasm",
        date = "2024-02-19",
        type = BlogItem.Type.TECH,
        summary = "Kotlin/Wasm, a new alternative to Kotlin/JS, shows promising performance for web applications using Kotlin Multiplatform. With its integration in Compose for Web, developers can build web UIs similarly to Android UIs using SKIA for rendering. Despite being in alpha, it offers easy setup for Kotlin-based web projects, though some features, like screen navigation, require workarounds.",
        composableItems = listOf {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LoadingDots()
            }
        }
    ),
    BlogItem(
        path = "article/tech/QualityInSoftwareDevelopment-MyValuesAndApproach.md",
        title = "Quality in Software Development: My Values and Approach",
        date = "2024-06-02",
        type = BlogItem.Type.TECH,
        summary = "Software quality involves both external factors like reliability and usability, and internal factors such as code readability and reduced complexity. Improving quality through refactoring and adopting new technologies is effective, but it's essential to define clear goals and ensure team cohesion."
    ),
    BlogItem(
        path = "article/tech/BlogUpdate2026.md",
        title = "ブログの構成をアップデートしました 2026",
        date = "2026-02-22",
        type = BlogItem.Type.TECH,
        summary = "ブログ構成のアップデート内容をまとめました。Kotlin 2.1とCompose 1.7への刷新、自作Markdownパーサーからの卒業、AST解析による本格的なIDE風シンタックスハイライト、そしてWasmにおける日本語フォントの「豆腐」問題の解決策について解説します。"
    ),
    BlogItem(
        path = "article/tech/ComposeArchitecture202602.md",
        title = "個人的に思うJetpack Composeにおける画面構成のパターンについて",
        date = "2026-02-24",
        type = BlogItem.Type.TECH,
        summary = "Jetpack Composeにおけるモダンな画面レイアウトアーキテクチャについて考察します。ViewModelの存在意義から、UiStateの切り分け、Route/Screen/Contentでの役割分割、さらにはActionsパターンとUseCaseの活用まで、実践的なベストプラクティスを紹介します。",
        composableItems = listOf(
            { PackageStructureDemo() },
            { ArchitectureDemo() }
        )
    ),
    BlogItem(
        path = "article/tech/AndroidAuto202602.md",
        title = "Androidエンジニアが愛車と通信したくてAndroid Autoで遊んでみた話",
        date = "2026-02-26",
        type = BlogItem.Type.TECH,
        summary = "Androidエンジニアが愛車（メルセデス・ベンツ）のデータを取得して車載ディスプレイに表示しようと奮闘した記録。AAOSとAndroid Autoの違いから、Car App Library (CarHardwareManager) を用いた車速データの取得・表示まで、実装例を交えて解説します。",
        composableItems = listOf(
            { AndroidAutoComparisonTable() },
            { AndroidAutoArchitectureComparison() },
            { AndroidAutoHardwareDataTable() },
            { AndroidAutoVideoPlayer("images/android_auto_second.mp4") },
            { AndroidAutoVideoPlayer("images/android_auto_third.mp4") },
            { AndroidAutoVideoPlayer("images/android_auto_fourth.mp4") },
            { AndroidAutoVideoPlaceholder() }
        )
    ),
)

fun BlogItem.getDestinationPath(): String {
    val destination = when (type) {
        BlogItem.Type.TECH -> Tech
        BlogItem.Type.TRAVEL -> Travel
        BlogItem.Type.BOOKS -> Books
    }
    val index = blogItems.filter { it.type == type }.indexOf(this)

    return destination.path + "/$index"
}
