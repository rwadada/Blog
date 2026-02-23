# ブログの構成をアップデートしました 2026

久しぶりにこのブログのシステムを大幅にアップデートしました。Kotlin Wasm (Compose for Web) を用いて構築されたこのサイトですが、いくつかの技術的負債や使いにくさを改善するため、抜本的な改修を行いました。
本記事では、今回のアップデートで実施した主な変更点について紹介します。

## 1. KotlinのアップデートとComposeの刷新

まずは基盤となるライブラリ群のアップデートです。
- **Kotlin**: 1.9系から `2.1.0` へアップデート
- **Compose Multiplatform**: `1.6`系から `1.7.0` へアップデート
- **Gradle**: `8.7` へアップデート

特にKotlin 2.0+からは、Compose CompilerがKotlinコンパイラに統合されたため、ビルドスクリプトの記述がスマートになりました。
アップデートに伴い、古い `compose.experimental { web.application {} }` の設定などは削除し、新しい Wasm 用の Webpack および開発サーバー設定 (`wasmJsBrowserDistribution`) へと移行しています。

## 2. 自作のMarkdownパーサーを除却し、専用ライブラリへ移行

これまでは、ブログ記事の描画に「自作の簡易Markdownパーサー」を使用していました。正規表現で部分的にタグを変換するだけの簡易的なものだったため、ネストされたリストや複雑なテーブルの表現に限界がありました。

今回、描画エンジンとして Mike Penz氏の開発する [multiplatform-markdown-renderer](https://github.com/mikepenz/multiplatform-markdown-renderer) を導入しました。
これにより、Markdownの構文解析（AST生成）がIntelliJのMarkdownパーサーベースで完全に行われるようになり、標準的なMarkdown記法が正確に描画されるようになりました。  
（めっちゃメンテ大変だったからすごくこのライブラリ見つけた時感動しましたねw）

ブログ固有のデザイン（見出しのオレンジの下線など）は、以下のようにカスタムコンポーネントとして `markdownComponents` に差し込むことで維持しています。

```kotlin
// 例: カスタム見出し1の実装
@Composable
fun CustomHeader1(content: String, node: ASTNode, style: TextStyle) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(fontSize = 32.sp, fontWeight = FontWeight.Black)
        )
        TitleLine() // オレンジの下線
    }
}
```

## 3. 本格的なシンタックスハイライトの導入

コードブロックの見た目も進化させました。以前の自作パーサーでは、`fun` や `val` などの少数のキーワードに色をつけるだけの正規表現ハイライトでした。しかも微妙に隙間が空いてしまうという残念な状況でもありました。

新しい環境では、`multiplatform-markdown-renderer-code` と `dev.snipme.highlights` ライブラリを組み合わせることで、**Android Studio (Darculaテーマ)** に近いシンタックスハイライトを実現しています。

```kotlin
val codeHighlights = remember(code) {
    Highlights.Builder()
        .code(code)
        .theme(SyntaxThemes.darcula(darkMode = true)) // Darculaテーマの適用
        .language(syntaxLanguage)
        .build()
}
```
ASTから言語情報を抽出し、コードを解析して `ColorHighlight` を Compose の `SpanStyle` にマッピングして描画するといったアプローチをとっています。

## 4. Compose for Web における日本語フォントの「豆腐」問題の解決

最後に、Webプラットフォーム（Wasm）向けの Compose Multiplatform 開発で頻繁に遭遇する**日本語フォントの表示問題**です。
Compose for Webの Canvas（Skia）描画では、システムフォントに適切にフォールバックされない場合、日本語が全て四角い「豆腐（□□□）」になってしまう現象が起こります（これがネックで過去の記事は英語です）。

これを解決するため、今回はGoogle Fontsから **Noto Sans JP** を取得し、プロジェクトの Compose Resources (`composeResources/font/`) に `.woff` 形式でバンドルしました。

```kotlin
val NotoSansJp: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.noto_sans_jp_regular, FontWeight.Normal),
        Font(Res.font.noto_sans_jp_bold, FontWeight.Bold),
        Font(Res.font.noto_sans_jp_black, FontWeight.Black)
    )

// MaterialTheme全体に適用
val AppTypography: Typography
    @Composable
    get() = Typography(defaultFontFamily = NotoSansJp)
```

Compose Multiplatform 1.6+ の新機能であるリソースAPIを使うことで、Wasm環境でも安定して指定のフォントを描画できるようになりました。

## 5. MarkdownとCompose UIの融合 (ComposableContent)

このブログでは、単なるテキストだけでなく、せっかくComposeで実装してるので、実際のComposeのコンポーネント（ローディングアニメーションやインタラクティブなUI）を記事内に直接埋め込みたいと思って作り込んでたCompose組み込みの仕組みはそのまま維持してます。。

具体的には、Markdownテキストの中に `[COMPOSABLE_INJECT_SLOT]` という独自のプレースホルダー文字列を配置し、描画時にそこで文字列を分割して、外部から渡されたカスタムの `@Composable` リストを差し込むというアプローチを採用しています。

```kotlin
val parts = content.split("[COMPOSABLE_INJECT_SLOT]")
parts.forEachIndexed { index, part ->
    Markdown(content = part, /*...*/)
    // パートの間にComposableなUIを挿入
    if (index < parts.size - 1 && index < composableItem.size) {
        composableItem[index]() 
    }
}
```
これにより、Markdownの書きやすさとCompose Multiplatformの高い表現力をシームレスに両立させています。

## 6. Wasm+Canvas環境における制限と今後の課題

最後に、Compose for Web (Wasm) が「HTMLのDOM要素」ではなく「HTML Canvas (Skia)」に全てを描画している仕様に起因する課題について触れておきます。

### テキスト選択問題（今回一部解決）
Canvas描画のため、標準状態ではブラウザの機能を使ったテキストのハイライトやコピー（ドラッグ選択）ができません。プログラムのコードなどをコピーしたい技術ブログとしては致命的でした。
この点については、今回のアップデートに合わせて記事コンポーネント全体を Compose が提供する `SelectionContainer` で囲む対応を入れました。これによりテキストの選択とコピーが可能になっています。

### SEOの課題（今後の取り組み）
Search Consoleのエラーや、Googleのクローラーによるインデックス化がうまく進まないという問題は**現在も継続中**です。当然ながらクローラーはCanvasの描画内容（ピクセルデータ）までは読み取ってくれないためです。
これを根本的に改善するには、初期ロード時にセマンティックなHTML要素を出力する必要があります（SEO目的で Compose HTML を併用したり、ビルド時の事前レンダリング/SSG機構を活用するなど）。
このSEO最適化は非常に難易度が高いですが、今後の重要なアップデート課題としてさらなる検証を進めていきます。（簡易的にやるならベースになるMDを見えないように配置すればいいのかなーとも思うんですが、負け感あってあんまりやりたくなくてw）

