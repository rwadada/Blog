# rwadada Blog — Design System

Personal blog of **Ryosuke Wada** (`rwadada`), a Japanese Android/Kotlin engineer. The blog is built with **Kotlin Wasm (Compose for Web / Compose Multiplatform)**, rendering everything to an HTML Canvas via Skia rather than DOM. Articles are written in Markdown (Japanese and English mixed) and loaded as resources.

**Source repo:** `rwadada/Blog` (GitHub) — Kotlin Multiplatform, Compose Multiplatform 1.7, Kotlin 2.1, Wasm/JS target.

---

## Product Overview

**Single product: Personal tech blog website**

- **URL surface:** Compose for Web, running in Wasm in a browser
- **Sections:** HOME · TECH · TRAVEL · BOOKS · PHOTO · CONTACT · SEARCH
- **Content types:** Long-form Markdown articles with embedded interactive Compose components (`[COMPOSABLE_INJECT_SLOT]` system), loading animations, video players, architecture diagrams
- **Author:** Ryosuke Wada — Android engineer. Topics: Kotlin/Wasm, Jetpack Compose, Android Auto, architecture, reading, travel, photography

---

## Content Fundamentals

### Tone
- **Personal and direct.** Writing is first-person ("私", "I"). The author shares opinions confidently.
- **Technical but accessible.** Code samples are included liberally. Technical depth is high but explained contextually.
- **Bilingual.** English titles/posts for broad tech topics; Japanese for more personal or nuanced pieces. Japanese prose is casual written standard (desu/masu), not stiff.
- **Occasional humor.** Parenthetical asides like `（めっちゃメンテ大変だったからすごくこのライブラリ見つけた時感動しましたねw）` — informal, self-deprecating. "w" (warau) used like "lol" in Japanese internet culture.
- **No emoji in UI.** Zero emoji used in nav, menus, or body. Clean, engineer aesthetic.
- **Tagline:** "Exploring the World through Books, Nature, and Android"
- **Footer copy:** `© 2026 Ryosuke Wada. Built with Kotlin Wasm.`

### Casing
- Headings in title case (English) or natural sentence case (Japanese)
- Menu items in ALL CAPS (HOME, TECH, TRAVEL…)
- Dates in ISO format: `YYYY-MM-DD`

### Examples
- Article title (EN): "About Kotlin Wasm", "Quality in Software Development: My Values and Approach"
- Article title (JP): "ブログの構成をアップデートしました 2026", "個人的に思うJetpack Composeにおける画面構成のパターンについて"
- Section header style: "ABOUT ME", "RECENT POSTS" — uppercase sans-serif

---

## Visual Foundations

### Colors
| Token | Hex | Usage |
|---|---|---|
| `--bg` | `#333333` | App background, header, footer |
| `--surface` | `#404040` | Cards on dark background |
| `--content-bg` | `#FFFFFF` | Article / main content area |
| `--fg` | `#FFFFFF` | Primary text on dark |
| `--fg-secondary` | `#CCCCCC` | Secondary text on dark, footer |
| `--fg-muted` | `#CCCCCC` | Unselected menu items |
| `--accent` | `#FF842A` | Selected nav, heading underlines, loading dots, borders |
| `--text-dark` | `#333333` | Body text on white content |
| `--border` | `#CCCCCC` | Dividers |
| `--code-bg` | `#17181A` | Code block background (near-black) |

### Typography
- **Primary font:** Noto Sans JP (Regular 400, Bold 700, Black 900) — bundled as `.woff` in `fonts/`
- **Fallback:** `sans-serif`
- **Sizes:** Header title 48sp · Sub-header 20sp · Section label bold · Body 14–16sp · Nav menu 12sp · Footer 12sp
- **No serif. No decorative fonts.** The entire site uses one typeface.

### Backgrounds
- App shell: solid `#333333` dark gray — no gradients, no images
- Hero header: full-bleed photograph (`header.jpg`) with a vertical dark gradient overlay (`rgba(0,0,0,0.2)` → `rgba(0,0,0,0.6)`)
- Content area: solid white `#FFFFFF`
- Cards on dark: `#404040` surface

### Layout
- **Header:** 50dp height, horizontal row — logo + name left, nav menu center, search icon right
- **Hero:** 500dp tall, full-bleed image with centered name + tagline text
- **Home bento grid:** Recent Posts · Products · Profile+Social row with weighted Card layout (`weight(2f)` + `weight(1f)`)
- **Content max-width:** stretches to fill; horizontal padding `24dp` on home, `16dp` on header

### Cards
- `RoundedCornerShape(24dp)` — heavily rounded
- `elevation = 4dp`
- Background `#404040` (surface)
- No border strokes on cards; separation via elevation shadow

### Heading style (in articles)
- H1: large bold text + **orange underline** (`TitleLine()` component — orange accent line below heading)
- Syntax highlight theme: Darcula (dark, IDE-style)

### Animation
- Loading dots: 3-dot pulsing animation, scale 0→1→0 with color cycling `#333333` → `#FF842A`, staggered 300ms delays. `LinearEasing`, 1200ms loop.
- Navigation: no page transitions visible; state-based composable swap
- No CSS-style hover states (Compose canvas)

### Hover/Interaction States
- Menu items: `#CCCCCC` (unselected) → `#FF842A` (selected/active)
- Clickable rows: Compose `clickable {}` modifier, no visual ripple noted in web
- Buttons: `TextButton` — text color changes only

### Iconography
- Custom geometric logo SVG/XML (see `assets/favicon.svg`) — abstract mark resembling stylized letter or orbital form
- Minimal icon set: logo, search, github, email, linkedin, instagram
- **Android Vector Drawable** (`.xml`) format in source; `.svg` for web favicon
- No icon fonts; no CDN icon libraries — every icon is individually sourced SVG/XML
- Icons tinted programmatically: unselected = `#CCCCCC`, accent = `#FF842A`, on dark = white

### Imagery
- Profile photo: `assets/profile.jpg`
- Hero header: `assets/header.jpg` — natural/outdoor photo, full-bleed cropped
- Article images: tech screenshots (wasm perf charts, Android Auto DHU screenshots)
- **Vibe:** natural, outdoor photography. Warm tones in header. No illustrations, no generated imagery.

### Corner Radii
- Cards: `24dp` (large, prominent)
- Buttons: default Material (TextButton — no visible pill shape)
- No border-radius on nav or header

### Shadows / Elevation
- Cards: `elevation = 4dp` — subtle Material shadow
- Hero text: `Shadow(color=Black@0.8, offset=(4,4), blurRadius=8)` for readability over image

### Code blocks
- Background: `#17181A`
- Darcula syntax theme
- Monospace font (system default in Compose)

---

## Iconography

Icons are stored in `assets/icons/`:
- `logo.xml` / `favicon.svg` — brand mark (abstract geometric)
- `search.xml` — search icon
- `github.xml` — GitHub logo
- `email.xml` — email icon
- `linkedin.xml` — LinkedIn logo

**Approach:** Individual SVG/vector assets, no icon font or sprite. Colors applied via tint at render time (`#CCCCCC` default, `#FF842A` accent, `#FFFFFF` on dark).

---

## File Index

```
README.md                        ← this file
SKILL.md                         ← agent skill descriptor
colors_and_type.css              ← CSS variables for colors and typography
fonts/
  noto_sans_jp_regular.woff
  noto_sans_jp_bold.woff
  noto_sans_jp_black.woff
assets/
  header.jpg                     ← hero background photo
  profile.jpg                    ← author profile photo
  favicon.svg                    ← brand logo SVG
  icons/                         ← individual icon assets
preview/                         ← design system card previews
ui_kits/
  blog/
    index.html                   ← interactive blog prototype
    Header.jsx
    HomePage.jsx
    ArticlePage.jsx
    TechListPage.jsx
```
