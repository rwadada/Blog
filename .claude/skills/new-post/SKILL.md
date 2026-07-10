---
name: new-post
description: Use this skill when adding or editing a blog article on this Kotlin Wasm blog (新しい記事の追加・投稿・記事の編集). Covers creating the Markdown file, registering the BlogItem entry, embedding interactive Compose components via inject slots, adding images/videos, and verifying the build.
user-invocable: true
---

# Adding a new blog post

This blog renders Markdown articles inside a Compose for Web (Kotlin/Wasm) app. Publishing a post requires **two coordinated changes**: the Markdown file and a `BlogItem` registration. Follow the steps in order.

## 1. Write the Markdown file

Create the article at:

```
composeApp/src/commonMain/resources/article/tech/<PascalCaseName>.md
```

- File name: PascalCase, optionally suffixed with `YYYYMM` for dated topics (e.g. `AndroidAuto202602.md`).
- Start with a single `# H1` title — it renders with the orange underline (`TitleLine`).
- Language: Japanese or English, matching the topic (Japanese for personal/nuanced pieces, English for broad tech topics). Japanese prose is casual desu/masu style. No emoji.
- Code fences should declare a language (` ```kotlin `) — syntax highlighting uses the Darcula theme via `dev.snipme.highlights` and picks the language from the fence info string.
- Only the TECH section is live; TRAVEL / BOOKS / PHOTO / CONTACT show a Coming Soon page.

## 2. Add images / videos

Place assets in `composeApp/src/commonMain/resources/images/` and reference them with a **relative path from the site root**:

```markdown
![alt text](images/my_screenshot.png)
```

They are fetched at runtime, not bundled via Compose Resources. Keep videos small (compress first; LFS is intentionally NOT used — it broke GitHub Pages serving in the past). Videos are embedded from Kotlin via `AndroidAutoVideoPlayer("images/foo.mp4")`-style components, not Markdown.

## 3. Register the BlogItem

Edit `feature/common/src/commonMain/kotlin/BlogItems.kt` and **append to the END of `blogItems`**:

```kotlin
BlogItem(
    path = "article/tech/MyNewPost.md",
    title = "記事タイトル",           // shown in lists; matches the Markdown H1
    date = "2026-07-10",              // ISO format YYYY-MM-DD
    type = BlogItem.Type.TECH,
    summary = "2〜3文の要約。",        // same language as the article; used on cards
    composableItems = listOf(...)     // only if using inject slots (see step 4)
)
```

**Ordering is load-bearing**: an article's URL is its index within its type (`#/tech/<index>`), computed from list position. Inserting in the middle silently changes every existing article's URL. Always append. List pages display newest-first by reversing, so appending is also correct chronologically.

## 4. (Optional) Embed interactive Compose components

To drop live Compose UI into the middle of an article:

1. In the Markdown, put this marker on its own line at each insertion point:

   ```
   %%%_COMPOSABLE_INJECT_SLOT_%%%
   ```

2. Provide one composable per marker, **in document order**, via `composableItems`:

   ```kotlin
   composableItems = listOf(
       { MyDemoComponent() },
       { AndroidAutoVideoPlayer("images/demo.mp4") }
   )
   ```

The number of markers must equal the number of entries in `composableItems` — a mismatch silently drops content. Components live in `feature/common/src/commonMain/kotlin/` (see `ArchitectureDemo.kt`, `PackageStructureDemo.kt`, `AndroidAutoVideoPlayer.kt` for examples). When building a new component, use the palette functions from `Colors.kt` (`backgroundColor()`, `surfaceColor()`, `selectedTextColor()`, …) — never hardcode colors — and follow the card idiom: `RoundedCornerShape(12.dp)`, `elevation = 0.dp`, `border = BorderStroke(1.dp, cardBorderColor())`, background `surfaceColor()`. Consult the `design-system` skill for full guidelines.

## 5. Verify

```sh
./gradlew :composeApp:wasmJsBrowserDistribution
```

must succeed (JDK 21). If possible, run the dev server and check the article renders — headings underlined, code highlighted, inject slots resolved, and Japanese text not showing as tofu (□):

```sh
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

The new post should appear in Recent Posts on HOME and at the top of the TECH list, and prev/next navigation on neighbouring articles should link to it.

## Checklist

- [ ] Markdown created under `article/tech/`, H1 title present
- [ ] Assets in `resources/images/`, referenced as `images/...`
- [ ] `BlogItem` appended (not inserted) to `blogItems` with correct `path`, ISO `date`, `summary`
- [ ] Inject-slot markers count == `composableItems` size
- [ ] `wasmJsBrowserDistribution` builds
