---
name: new-post
description: >
  このブログ（Kotlin/Wasm + Compose for Web 製）への記事の追加・編集を行うスキル。
  記事アイデアの壁打ち（深掘り対話）→ 構成合意 → Markdown 執筆 → インタラクティブデモ（Composable）実装 →
  BlogItems.kt への登録（ルーティング・一覧・検索への繋ぎこみ）→ ビルド検証 → commit & draft PR まで一気通貫で行う。
  ユーザーが「記事を書きたい」「ブログに投稿したい」「〜について書こうと思ってる」「記事のネタがあるんだけど」
  「これブログ記事にして」のように、記事の作成・追加・執筆・アイデア相談・既存記事の編集を持ちかけたら、
  アイデアがまだ漠然としていても必ずこのスキルを使うこと。出し先（Zenn かこのブログか）の判定も
  このスキルが担うので、出し先未定の記事相談でも起動してよい。
user-invocable: true
---

# Blog 記事作成・編集スキル

このブログは Compose for Web (Kotlin/Wasm) アプリの中で Markdown 記事をレンダリングする。
公開には **Markdown ファイルと `BlogItem` 登録の2つの連動した変更**が必要。

アイデア段階から始まる場合は Phase 1 から、書く内容が確定している場合や既存記事の編集は Phase 3 以降から入る。

## 全体フロー

0. **出し先チェック** — Zenn かこのブログか、記事の性質から判定する
1. **壁打ち** — アイデアを深掘りし、読者と切り口を定める
2. **構成合意** — アウトラインを提示し、合意してから書く
3. **執筆** — 既存記事のトーンに合わせて Markdown を書く
4. **画像・動画** — 素材を配置する
5. **デモ実装** — 効果的な箇所にインタラクティブな Composable デモを差し込む
6. **登録** — BlogItems.kt に追加（ルーティング・一覧・検索は自動反映）
7. **検証 → commit & draft PR**

## Phase 0: 出し先チェック

書き始める前に、そのネタがこのブログ向きかを判定する:

- **このブログ向き**: 体験ベースの語り・ポートフォリオ性の強い話（作ったもの・愛車ネタ等）、
  このブログ自体の話（構成変更・不具合対応記）、インタラクティブデモが活きるネタ
- **Zenn 向き**: 特定の個人体験に依存しない汎用的な技術解説（広い読者への解説記事）

Zenn 向きと判定したら、理由を添えてユーザーに提案して終了する（Zenn 記事の執筆はこのスキルの範囲外。
Zenn リポジトリはこのマシンには無い）。迷うケースや両方に出せるネタはユーザーに判断を仰ぐ。
ユーザーが「このブログに書く」と明言している場合はこのフェーズをスキップしてよい。

## Phase 1: 壁打ち

記事の質は書き始める前の解像度で決まる。アイデアを受け取ったら、すぐ書き始めずに対話で深掘りする。

- 質問は一度に 2〜4 個まで。尋問にならないよう、自分の理解や仮説を添えて投げる
- 掘るべき観点: **誰に読ませたいか**（想定読者の前提知識）、**何が新しいか**（既存記事や世間の解説との差分）、
  **実体験・具体例はあるか**（このブログの記事は体験ベースの語りが核になっている）、
  **結論・主張は何か**（「〜してみた」系でも学びの一文がある）
- ユーザーの回答が薄い観点は、こちらから選択肢や仮説を出して反応をもらう方が速い
- アイデアが最初から具体的（構成まで頭にある様子）なら、深掘りは 1 往復に留めて構成合意へ進む

## Phase 2: 構成合意

壁打ちの内容をアウトライン（見出し案 + 各セクション 1〜2 行の要旨 + デモ挿入候補箇所）にまとめて提示し、
合意を得てから執筆に入る。ここで手戻りを潰すのが目的なので、「この構成で書きます」ではなく
変えてほしい点を聞く形で出す。タイトル案も 2〜3 個併記する。

## Phase 3: 執筆

書く前に必ず既存記事を 1〜2 本読んでトーンを掴む（`composeApp/src/commonMain/resources/article/tech/` の
日付が新しいもの。例: `BlogUpdate2026.md`, `ComposeArchitecture202602.md`）。

ファイルの置き場所:

```
composeApp/src/commonMain/resources/article/tech/<PascalCaseName>.md
```

- ファイル名: PascalCase。時事性のあるトピックは `YYYYMM` サフィックス（例: `AndroidAuto202602.md`）。
  **ファイル名から導出される slug がそのまま URL・ルーティングのキーになる**ので、公開後に変えない前提で命名する
- 1 行目は単一の `# H1` タイトル（オレンジ下線 `TitleLine` 付きでレンダリングされる）。直後に導入 2〜3 段落、以降 `## セクション`
- 言語: 個人的・ニュアンス重視の話は日本語、広い技術トピックは英語でもよい。日本語はカジュアルなです/ます調で、
  たまに砕けた本音（「めっちゃメンテ大変だったから〜」）が混ざる文体。絵文字は使わない
- コードフェンスは言語指定付き（` ```kotlin `）— シンタックスハイライトは `dev.snipme.highlights`（Darcula テーマ）が
  フェンスの言語指定を見る
- 詳細ページが実装されているのは TECH のみ。TRAVEL / BOOKS / PHOTO / CONTACT は Coming Soon 表示

執筆後、本文をユーザーにレビューしてもらう。修正の往復はここで済ませる（PR 後の直しより安い）。

## Phase 4: 画像・動画

素材は `composeApp/src/commonMain/resources/images/` に置き、**サイトルートからの相対パス**で参照する:

```markdown
![alt text](images/my_screenshot.png)
```

- 実行時に fetch される（Compose Resources へのバンドルではない）
- 動画は事前に圧縮して小さく保つ。**LFS は意図的に使わない**（過去に GitHub Pages 配信が壊れた）
- 動画の埋め込みは Markdown ではなく Kotlin 側から `AndroidAutoVideoPlayer("images/foo.mp4")` 系の
  コンポーネント（inject slot 経由）で行う
- ユーザーから素材をもらう必要がある場合はプレースホルダーを置いてその旨を明示する

## Phase 5: （任意）インタラクティブデモ実装

このブログの特色は記事内で動く Compose デモ。文章より動きで伝わる箇所（アーキテクチャの流れ、
アニメーション、比較表など）があれば実装を提案する。無理に入れる必要はない — 静的な記事も普通にある。

1. Markdown 内の挿入位置に、このマーカーを**単独行**で置く:

   ```
   %%%_COMPOSABLE_INJECT_SLOT_%%%
   ```

2. マーカー 1 つにつき Composable を 1 つ、**文書内の登場順**で `composableItems` に渡す:

   ```kotlin
   composableItems = listOf(
       { MyDemoComponent() },
       { AndroidAutoVideoPlayer("images/demo.mp4") }
   )
   ```

**マーカー数と `composableItems` の要素数は必ず一致させる** — ズレると内容が黙って欠落する。
コンポーネントは `feature/common/src/commonMain/kotlin/` にトップレベル（パッケージ宣言なし）で置く
（手本: `ArchitectureDemo.kt`, `PackageStructureDemo.kt`, `AndroidAutoVideoPlayer.kt`）。

実装時の決まりごと:

- 色は `Colors.kt` のパレット関数（`backgroundColor()`, `surfaceColor()`, `selectedTextColor()`,
  `secondaryTextColor()`, `cardBorderColor()` …）を使い、ハードコードしない（ライト/ダーク両対応のため）
- カードのイディオム: `RoundedCornerShape(12.dp)`, `elevation = 0.dp`,
  `border = BorderStroke(1.dp, cardBorderColor())`, 背景 `surfaceColor()`。詳細は `design-system` スキル参照
- **material-icons は依存に無く追加も不可**（現行 Kotlin/Wasm と klib 非互換）。アイコンが要る場合は
  `NavigationIcons.kt` のように ImageVector を手書きするか、テキスト・図形で表現する
- Material2 (`androidx.compose.material`) を使用。Material3 は入っていない

## Phase 6: 登録

`feature/common/src/commonMain/kotlin/BlogItems.kt` の `blogItems` リスト**末尾に追加**する:

```kotlin
BlogItem(
    path = "article/tech/MyNewPost.md",
    title = "記事タイトル",           // 一覧に表示。Markdown の H1 と一致させる
    date = "2026-07-10",              // ISO 形式 YYYY-MM-DD（今日の日付）
    type = BlogItem.Type.TECH,
    summary = "3〜4文の要約。",        // 記事と同じ言語。一覧カードと検索対象
    composableItems = listOf(...)     // inject slot を使う場合のみ（Phase 5 参照）
)
```

- **必ず末尾に追加**する。一覧は `reversed()` で表示されるため末尾 = 最新として先頭に出るし、
  記事詳細の Older/Newer Post ナビもリスト順から生成される
- ルーティングは slug（ファイル名から自動導出）ベースの `Route.TechArticle(slug)` に自動接続される。
  Tech 一覧・Home の Recent Posts・検索への繋ぎこみもすべて自動 — ナビゲーションコードの手書きは不要

## Phase 7: 検証 → commit & draft PR

1. ビルドが通ることを確認（JDK 21）:

   ```sh
   ./gradlew :composeApp:wasmJsBrowserDistribution
   ```

2. 可能なら開発サーバーを起動し、目視確認をユーザーに促す — 見出しの下線、コードハイライト、
   inject slot の解決、日本語が豆腐（□）になっていないか、HOME の Recent Posts と TECH 一覧の先頭に
   新記事が出ているか、前後記事のナビが繋がっているか:

   ```sh
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
   ```

3. ブランチを切って記事・素材・デモ・BlogItems.kt をコミットし、draft PR を作成する
   （PR 本文に記事タイトル・概要・確認方法を書く）。`kotlin-js-store/` のロックファイルが変わった場合は
   それもコミットする。公開判断はユーザーが PR 上で行う。main への直接 push はしない

## チェックリスト

- [ ] 壁打ち → 構成合意を経てから執筆した（アイデアが具体的な場合は短縮可）
- [ ] Markdown を `article/tech/` に作成、H1 タイトルあり、既存記事とトーンが揃っている
- [ ] 素材は `resources/images/` に配置し `images/...` で参照
- [ ] `BlogItem` を末尾に**追加**（挿入ではなく）。`path`・ISO `date`・`summary` が正しい
- [ ] inject slot マーカー数 == `composableItems` 数
- [ ] `wasmJsBrowserDistribution` がビルド成功
- [ ] draft PR 作成済み（main へ直接 push していない）
