# Blog

[Ryosuke Wada (rwadada)](https://github.com/rwadada) の個人ブログです。
**Kotlin/Wasm + Compose Multiplatform (Compose for Web)** で構築しており、DOM ではなく Skia を介して HTML Canvas に全 UI を描画する SPA です。ビルド成果物は GitHub Pages でホスティングしています。

## コンセプト

- **「AndroidエンジニアがAndroidの技術でWebを作る」** — Jetpack Compose と同じ書き味 (`@Composable`) でブログ全体を実装しています。HTML/CSS/JS は起動用の `index.html` のみで、レイアウト・タイポグラフィ・アニメーションはすべて Compose のコードです。
- **記事は Markdown、リッチ表現は Compose** — 記事本文は Markdown で書きつつ、記事の途中にインタラクティブな Compose コンポーネント（アーキテクチャ図デモ、動画プレイヤー、比較テーブルなど）を差し込める「Composable インジェクトスロット」の仕組みを持っています。
- **ダークテーマ × オレンジアクセントのエンジニア的デザイン** — 装飾を抑えたダーク UI に、アクセントカラーのオレンジを見出し下線・選択中ナビ・リンクに限定して使用します。絵文字は使わず、ナビゲーションは大文字表記（HOME / TECH / …）で統一しています。

## 技術スタック

| 分類 | 技術 |
|---|---|
| 言語 | Kotlin 2.1.0 (`wasmJs` ターゲット) |
| UI | Compose Multiplatform 1.7.0 (Compose for Web / Canvas + Skia 描画) |
| Markdown 描画 | [multiplatform-markdown-renderer](https://github.com/mikepenz/multiplatform-markdown-renderer) 0.27.0（IntelliJ Markdown パーサーによる AST 解析） |
| シンタックスハイライト | `multiplatform-markdown-renderer-code` + `dev.snipme.highlights`（Darcula テーマ） |
| 画像読み込み | Coil3 + Ktor |
| フォント | Noto Sans JP（`.woff` を Compose Resources にバンドル） |
| CI/CD | GitHub Actions → GitHub Pages（`github-pages` ブランチへデプロイ） |

## モジュール構成

```
composeApp/                     エントリポイント (wasmJs) とリソース
├── src/wasmJsMain/kotlin/main.kt   CanvasBasedWindow 起動・ハッシュルーティング
└── src/commonMain/resources/
    ├── article/tech/*.md           記事本文 (Markdown)
    └── images/                     記事用の画像・動画

feature/
├── common/                     共通基盤
│   ├── Colors.kt / Theme.kt        カラーパレット・タイポグラフィ (Noto Sans JP)
│   ├── Destination.kt              ルーティング定義 (sealed interface)
│   ├── BlogItems.kt                記事メタデータのレジストリ
│   ├── markdown/                   Markdown 描画・カスタムレンダラー
│   ├── Header.kt / Footer.kt       共通レイアウト
│   └── （記事埋め込み用のデモ・動画プレイヤーなど）
├── home/                       ホーム画面 (ヒーロー画像・Recent Posts・プロフィール)
└── tech/                       Tech 記事一覧・記事詳細
```

## 実装のポイント

### ルーティング

`window.location.hash` ベースの SPA ルーティングです。`#/tech/0` のようなパスを `Destination`（sealed interface）に解決し、`popstate` イベントで戻る/進むにも追従します。ページ遷移は Composable の差し替えで行い、遷移のたびにスクロール位置を先頭へリセットします。

### 記事システム

1. 記事メタデータ（パス・タイトル・日付・サマリー・埋め込みコンポーネント）を `feature/common/.../BlogItems.kt` の `blogItems` に登録します。
2. 本文の Markdown は Compose Resources ではなく **実行時に `fetch` で取得**します（`ReadFileUseCase` がメモリキャッシュ付きで読み込み）。
3. Markdown 中に `%%%_COMPOSABLE_INJECT_SLOT_%%%` と書いた行が、`BlogItem.composableItems` に登録した Composable と**登場順に**置き換わります。これにより Markdown 記事の中にインタラクティブな Compose UI を埋め込めます。

### デザインシステム

カラーは `feature/common/.../Colors.kt` に集約しています。

| 用途 | 色 |
|---|---|
| 背景 | `#1E1E1E` |
| カード表面 | `#252525`（角丸 12dp・白 7% の 1dp ボーダー・elevation 0） |
| アクセント（選択ナビ・見出し下線・リンク） | `#FF842A` |
| 本文テキスト | `#AAAAAA` / 強調 `#F0F0F0` |
| コードブロック背景 | `#141416`（Darcula ハイライト） |

日本語表示は Compose for Web の Canvas 描画でフォントフォールバックが効かず「豆腐（□）」になるため、**Noto Sans JP (Regular/Bold/Black) を `.woff` でバンドル**して `MaterialTheme` 全体に適用しています。

詳細なデザインガイドラインは `.claude/skills/design-system/` を参照してください。

## 開発

JDK 21 を使用します。

```sh
# 開発サーバー起動 (ホットリロード付き)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# プロダクションビルド
./gradlew :composeApp:wasmJsBrowserDistribution
# => composeApp/build/dist/wasmJs/productionExecutable/ に出力
```

### デプロイ

`main` ブランチへの push をトリガーに GitHub Actions (`.github/workflows/main.yml`) がプロダクションビルドを実行し、`github-pages` ブランチへデプロイします。

### 記事の追加

記事追加の手順は Claude Code 用 skill として `.claude/skills/new-post/` にまとめています。手動で行う場合の概要:

1. `composeApp/src/commonMain/resources/article/tech/` に Markdown を作成
2. 画像・動画は `composeApp/src/commonMain/resources/images/` に配置
3. `feature/common/src/commonMain/kotlin/BlogItems.kt` の `blogItems` **末尾**に `BlogItem` を追加（既存記事の URL はリスト内の順序から決まるため、途中に挿入しない）
