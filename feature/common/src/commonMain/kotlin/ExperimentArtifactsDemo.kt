import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class ExperimentTab(val label: String) {
    METRICS("Metrics"),
    ARTIFACTS("Artifacts"),
    MERGE_DIFF("Merge Diff"),
    GUARDRAIL("Guardrail & Spec"),
}

private val patternAColor = Color(0xFFE57373)
private val patternBColor = Color(0xFF4DADF7)
private val passGreenColor = Color(0xFF81C784)

@Composable
fun ExperimentArtifactsDemo() {
    var selectedTab by remember { mutableStateOf(ExperimentTab.METRICS) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(surfaceColor())
            .border(1.dp, cardBorderColor(), RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Text(
            text = "比較実験の計測データ & 実物成果物ビューアー",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = accentTextColor()
        )
        Text(
            text = "AWS公式 AI-DLC (v2.11.4) vs 決定論的ガードレール / お題: TripSplit (React + TS + Vitest)",
            fontSize = 12.sp,
            color = secondaryTextColor(),
            modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
        )

        // Tab bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExperimentTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) selectedTextColor().copy(alpha = 0.18f) else backgroundColor())
                        .border(
                            width = 1.dp,
                            color = if (isSelected) selectedTextColor() else cardBorderColor(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedTab = tab }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = tab.label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) selectedTextColor() else secondaryTextColor()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            ExperimentTab.METRICS -> MetricsTabContent()
            ExperimentTab.ARTIFACTS -> ArtifactsTabContent()
            ExperimentTab.MERGE_DIFF -> MergeDiffTabContent()
            ExperimentTab.GUARDRAIL -> GuardrailTabContent()
        }
    }
}

@Composable
private fun MetricsTabContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Legend
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendChip(color = patternAColor, label = "A案: AWS公式 AI-DLC v2.11.4")
            LegendChip(color = patternBColor, label = "B案: 決定論的ガードレール")
        }

        MetricBarCard(
            title = "初期セットアップ追加ファイル数 (コード1行書く前)",
            aLabel = "398 files (+299,775 lines)",
            bLabel = "5 files (+366 lines)",
            aRatio = 1.0f,
            bRatio = 0.03f,
            note = "A案は .claude/ 388 + aidlc/ 16 等。feature スコープ起動時に 33ステージ・30承認ゲートを生成"
        )
        MetricBarCard(
            title = "全4ステップ総トークン消費量 (Input + CacheRead + Output)",
            aLabel = "5,823,273 tokens (1.91x)",
            bLabel = "3,046,347 tokens (1.00x)",
            aRatio = 1.0f,
            bRatio = 0.52f,
            note = "A案の CacheRead は 516万トークン (B案 260万の 1.98x)。Output も 14.6万 vs 9.5万 (1.54x)"
        )
        MetricBarCard(
            title = "全4ステップ合計所要時間 (5タスク合計)",
            aLabel = "1,583s / 26m23s (1.51x)",
            bLabel = "1,050s / 17m30s (1.00x)",
            aRatio = 1.0f,
            bRatio = 0.66f,
            note = "特に Step 1 は 418s vs 204s (2.05x)、Step 4 並列タスクも約 1.7x の差"
        )
        MetricBarCard(
            title = "プロジェクト文書量 (.claude/ フレームワーク本体を除く)",
            aLabel = "71,012 chars / 1,330 lines (20.7x)",
            bLabel = "3,432 chars / 66 lines (1.0x)",
            aRatio = 1.0f,
            bRatio = 0.05f,
            note = "B案の spec.md (1,904文字) はコードから自動生成・手編集禁止。A案は初期インセプション文書が化石化"
        )
        MetricBarCard(
            title = "Step 4 並列マージ時のコンフリクト (コード衝突は両案 0 件)",
            aLabel = "4 files / 5 markers (ドキュメント 4/4 全滅)",
            bLabel = "0 files / 0 markers (自動マージ -> CI 1.00s PASS)",
            aRatio = 1.0f,
            bRatio = 0.02f,
            note = "A案は requirements.md (FR-8 衝突) や tasks.md (Unit 13-16 衝突) など文書4件すべてでマージ停止"
        )
    }
}

@Composable
private fun LegendChip(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color)
        )
        Text(text = label, fontSize = 11.sp, color = secondaryTextColor())
    }
}

@Composable
private fun MetricBarCard(
    title: String,
    aLabel: String,
    bLabel: String,
    aRatio: Float,
    bRatio: Float,
    note: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor())
            .border(1.dp, cardBorderColor(), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = accentTextColor())
        BarRow(tag = "A", valueLabel = aLabel, ratio = aRatio, color = patternAColor)
        BarRow(tag = "B", valueLabel = bLabel, ratio = bRatio, color = patternBColor)
        Text(text = note, fontSize = 11.sp, color = secondaryTextColor(), lineHeight = 16.sp)
    }
}

@Composable
private fun BarRow(tag: String, valueLabel: String, ratio: Float, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = tag, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = valueLabel, fontSize = 11.sp, color = accentTextColor(), fontFamily = FontFamily.Monospace)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(surfaceColor())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(ratio.coerceIn(0.02f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
    }
}

@Composable
private fun ArtifactsTabContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CodePanel(
            title = "A案の成果物ツリー (src 3,699行 / test 1,931行)",
            accentColor = patternAColor,
            code = """
.claude/                    # 388 files (AWS AI-DLC)
  rules/tripsplit-rules.md  # 8規約 (自然言語)
aidlc/spaces/default/       # 1,330 lines / 71,012 chars
  memory/project.md         # [CONFLICT]
  intents/261119-trip-split-core/
    aidlc-state.md          # Step 1 のまま化石化
    ideation/intent-capture/
      intent-statement.md   # Step 1 のまま化石化
      stakeholder-map.md
      intent-capture-questions.md
    inception/
      requirements-analysis/requirements.md  # [CONFLICT]
      domain-design/domain-design.md         # [CONFLICT]
    construction/
      delivery-planning/tasks.md             # [CONFLICT]
src/
  domain/  trip.ts, settlement.ts, csvExport.ts,
           memberBalanceSummary.ts, money.ts, clock.ts
  data/    localStorageTripRepository.ts,
           browserCsvDownloader.ts, systemClock.ts
  features/trips/
           TripWorkspace.tsx, MemberBalanceSummaryView.tsx,
           tripWorkspaceReducer.ts
tests/     8 test files / 37 tests (Step 4a時点)
            """.trimIndent(),
            modifier = Modifier.weight(1f)
        )

        CodePanel(
            title = "B案の成果物ツリー (src 2,333行 / test 1,824行)",
            accentColor = patternBColor,
            code = """
AGENTS.md                   # 34 lines (薄いガイド)
CLAUDE.md                   # 1 line (@AGENTS.md)
scripts/gen-spec.ts         # 仕様書自動生成 + --check
docs/generated/spec.md      # 31 lines (自動生成・手編集禁止)

src/
  domain/  trip.ts, settlement.ts, deadline.ts,
           csvExport.ts, memberSummary.ts,
           money.ts, clock.ts
  data/    tripRepository.ts, csvDownloader.ts,
           systemClock.ts
  design-system/
           tokens.ts, components.tsx
  features/trip/
           TripView.tsx, MemberBalanceSummaryView.tsx,
           tripState.ts, tripReducer.ts

tests/                      # 11 test files / 55 tests
  architecture.test.ts      # 8規約を静的検査 (9 tests)
  trip.test.ts, settlement.test.ts, deadline.test.ts,
  csvExport.test.ts, memberSummary.test.ts,
  tripRepository.test.ts, tripReducer.test.ts, ...
            """.trimIndent(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MergeDiffTabContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CodePanel(
            title = "A案: git merge pattern-a-4b の実出力 (コード衝突0・ドキュメント4/4衝突で停止)",
            accentColor = patternAColor,
            code = """
Auto-merging src/features/trips/TripWorkspace.tsx   # OK
Auto-merging tests/TripWorkspace.test.tsx           # OK
CONFLICT (content): Merge conflict in .../tasks.md
CONFLICT (content): Merge conflict in .../domain-design.md
CONFLICT (content): Merge conflict in .../requirements.md
CONFLICT (content): Merge conflict in aidlc/spaces/default/memory/project.md
Automatic merge failed; fix conflicts and then commit the result.

--- requirements.md で実際に起きた FR-8 の衝突 ---
<<<<<<< HEAD
### FR-8: 立替履歴・精算結果の CSV エクスポート
- FR-8.1: ユーザーは選択中の旅行グループの...
=======
### FR-8: メンバー別収支サマリービュー（立替合計・負担合計・最終収支の比較）
- FR-8.1: 選択中の旅行グループについて...
>>>>>>> pattern-a-4b
            """.trimIndent(),
            modifier = Modifier.fillMaxWidth()
        )

        CodePanel(
            title = "B案: git merge pattern-b-4b & npm run ci の実出力 (コンフリクト0・1.00sで全PASS)",
            accentColor = passGreenColor,
            code = """
Auto-merging docs/generated/spec.md
Auto-merging src/features/trip/TripView.tsx
Merge made by the 'ort' strategy.  # CONFLICT = 0

$ npm run ci
> tsc --noEmit          # PASS
> vitest run            # Test Files 11 passed (11), Tests 55 passed (55) in 1.00s
  - tests/architecture.test.ts (9 tests) PASS
> tsx scripts/gen-spec.ts --check
OK: docs/generated/spec.md は最新です。
            """.trimIndent(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GuardrailTabContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CodePanel(
            title = "B案の決定論的テスト抜粋 (tests/architecture.test.ts - A案の交差検証にも使用)",
            accentColor = patternBColor,
            code = """
// 規約3: 状態は Discriminated Union (局面フラグ禁止)
export function detectFlagProperties(code: string): string[] {
  const forbidden = /\b(isLoading|isSaving|isSettled|isError|isOverdue|showErrors)\s*\??\s*:/g;
  return Array.from(code.matchAll(forbidden), (m) => m[1]!);
}

// 規約4: 現在時刻は Clock 注入 (systemClock.ts 以外で new Date()/Date.now() 禁止)
export function detectDirectDateUsage(code: string): string[] {
  return Array.from(code.matchAll(/\b(?:new\s+Date\s*\(\s*\)|Date\.now\s*\(\s*\))/g), (m) => m[0]);
}

// 空振り防止の自己検査テスト (テスト自体の健全性を担保)
it('違反コードを確実に検出できる', () => {
  expect(detectFlagProperties('type S = { isLoading: boolean }')).toEqual(['isLoading']);
  expect(detectDirectDateUsage('const t = new Date()')).toEqual(['new Date()']);
});
            """.trimIndent(),
            modifier = Modifier.fillMaxWidth()
        )

        CodePanel(
            title = "B案で自動生成された仕様書 (docs/generated/spec.md 実物抜粋)",
            accentColor = passGreenColor,
            code = """
# TripSplit 自動生成仕様書
> このファイルは npm run spec:gen によりコードから自動生成されます。手で編集しないでください。

## Domain 層 (src/domain/)
- src/domain/clock.ts: Clock
- src/domain/csvExport.ts: escapeCsvField, formatCsvRow, generateTripCsv, buildTripCsvFilename
- src/domain/deadline.ts: MS_PER_DAY, SettlementDeadlineStatus, evaluateSettlementDeadline, ...
- src/domain/memberSummary.ts: BalanceDirection, MemberBalanceSummaryItem, buildMemberBalanceSummary
- src/domain/settlement.ts: allocateExpenseShares, calculateSettlement, ...
- src/domain/trip.ts: SplitRule, SettlementCancellation, SettlementPhase, TripGroup, ...
            """.trimIndent(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CodePanel(
    title: String,
    accentColor: Color,
    code: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(codeBlockBackgroundColor())
            .border(1.dp, accentColor.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = code,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            color = accentTextColor(),
            lineHeight = 16.sp
        )
    }
}
