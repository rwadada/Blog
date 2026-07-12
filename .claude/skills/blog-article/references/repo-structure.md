# リポジトリ構造リファレンス（記事追加に関わる部分）

## 記事が画面に出るまでの仕組み

```
composeApp/src/commonMain/resources/article/tech/<Slug>.md   ← 記事本体
        │  path で参照
        ▼
feature/common/src/commonMain/kotlin/BlogItems.kt            ← blogItems リストに登録
        │  slug = path のファイル名から自動導出
        ▼
Route.TechArticle(slug)                                       ← ルーティング（Route.kt / App.kt の NavHost）
        │
        ├─ feature/tech/TechPage.kt        : Tech 一覧 + 記事詳細（前後記事ナビ付き）
        ├─ feature/home/RecentPostsContent : Home の最新3件
        └─ feature/common/SearchPage.kt    : title / summary / date の部分一致検索
```

登録は BlogItems.kt の 1 箇所だけ。ルーティング・一覧・検索への手動の繋ぎこみは不要。

## BlogItem のスキーマ

```kotlin
BlogItem(
    path = "article/tech/MyArticle202607.md",  // resources からの相対パス
    title = "記事タイトル",                      // 一覧・詳細・検索に表示
    date = "2026-07-11",                        // YYYY-MM-DD
    type = BlogItem.Type.TECH,                  // TECH / TRAVEL / BOOKS（詳細ページは TECH のみ実装済み）
    summary = "3〜4文の要約。一覧カードと検索対象。",
    composableItems = listOf(                   // 省略可。デモがある場合のみ
        { MyDemoComposable() },
    )
)
```

- `blogItems` リストの**末尾**に追加する。一覧側が `reversed()` するので末尾が最新扱い
- `slug` は `path.substringAfterLast('/').removeSuffix(".md")` — ファイル名がそのまま URL キー
- 記事詳細の「Older/Newer Post」ナビもリスト順から自動生成される

## インタラクティブデモ（Composable 差し込み）

- Markdown 側: 差し込みたい位置に `%%%_COMPOSABLE_INJECT_SLOT_%%%` を**単独行**で書く
- 描画側: `markdown/MarkdownContent.kt` がこの文字列で本文を split し、
  `composableItems` を**登場順**に差し込む。マーカー数と composableItems の数・順序を一致させること
- デモ本体は `feature/common/src/commonMain/kotlin/` にトップレベル（パッケージ宣言なし）で置く。
  参考実装: `ArchitectureDemo.kt`（アニメーション付き図解）, `PackageStructureDemo.kt`,
  `AndroidAutoTables.kt`（表）, `AndroidAutoVideoPlayer.kt`（動画埋め込み）

### デモ実装の制約（Kotlin/Wasm）

- **material-icons は依存に無く追加も不可**（現行 Kotlin/Wasm と klib 非互換）。
  アイコンは `NavigationIcons.kt` のように ImageVector 手書き、またはテキスト・図形で代用
- Material2 (`androidx.compose.material`) を使用。Material3 は入っていない
- 色・テーマは `Colors.kt` / `Theme.kt` のヘルパー（`backgroundColor()`, `surfaceColor()`,
  `accentTextColor()`, `secondaryTextColor()`, `selectedTextColor()`, `cardBorderColor()` など）を使い、
  ライト/ダーク両テーマで破綻しないようにする

## Markdown レンダリング

- mikepenz/multiplatform-markdown-renderer + カスタムコンポーネント（`markdown/CustomRenderers.kt`）
- 見出し・コードブロック・表・リスト・リンク・画像は標準記法でOK
- 画像・動画の実体は `composeApp/src/commonMain/resources/images/` に置き、`images/<ファイル名>` で参照

## 検証コマンド

```bash
./gradlew build                            # コンパイル + 全チェック
./gradlew wasmJsBrowserDevelopmentRun      # 開発サーバー起動（目視確認用）
```

## コミット対象

- 記事 md / 画像 / デモ Composable / BlogItems.kt の差分
- `kotlin-js-store/` のロックファイルが変わった場合はそれもコミットする（このリポジトリの方針）
