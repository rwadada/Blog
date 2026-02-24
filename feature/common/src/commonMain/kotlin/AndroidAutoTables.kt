import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp

@Composable
fun AndroidAutoComparisonTable() {
    val tableData = listOf(
        listOf("特徴", "Android Automotive OS (AAOS)", "Android Auto"),
        listOf("本体はどこ？", "車のシステムそのものに組み込まれている", "スマホの中（車はただのディスプレイ）"),
        listOf("スマホは必要？", "不要（車単体で動く）", "必須（スマホと車を繋ぐ）"),
        listOf("アプリの動き", "車載器のCPUで処理される", "スマホのCPUで処理され、画面だけ車に送る"),
        listOf("今回の私の場合", "車が対応してなかったので NG", "スマホがあればできるので OK")
    )

    NativeTable(
        data = tableData,
        columnWidths = listOf(140.dp, 260.dp, 280.dp)
    )
}

@Composable
fun AndroidAutoHardwareDataTable() {
    val tableData = listOf(
        listOf("カテゴリ", "取得できるデータの例", "アプリのアイデア妄想"),
        listOf("車両情報 (Model)", "メーカー名、車種名、年式", "アプリ内のUIを乗っている車に合わせてカスタマイズする"),
        listOf("走行状態 (Speed, Mileage)", "車速、オドメーター（総走行距離）", "カスタムスピードメーターを作る"),
        listOf("エネルギー (EnergyLevel)", "燃料残量、バッテリー残量、航続可能距離", "ガソリンが減ったら勝手に最寄りの安いスタンドを探す機能"),
        listOf("センサー (CarSensors)", "加速度、ジャイロ、コンパス、位置情報", "自分の運転のG（加速度）を可視化して、丁寧な運転をスコア化"),
        listOf("その他 (TollCardなど)", "ETCカードの挿入状態", "高速のインターに近づいた時にカードが入ってなかったら警告")
    )

    NativeTable(
        data = tableData,
        columnWidths = listOf(180.dp, 240.dp, 280.dp)
    )
}

@Composable
private fun NativeTable(data: List<List<String>>, columnWidths: List<Dp>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, secondaryTextColor().copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .horizontalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .background(surfaceColor())
            ) {
            data.forEachIndexed { rowIndex, rowData ->
                val isHeader = rowIndex == 0
                val bgColor = if (isHeader) secondaryTextColor().copy(alpha = 0.1f) else Color.Transparent

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgColor)
                ) {
                    rowData.forEachIndexed { colIndex, cellText ->
                        Box(
                            modifier = Modifier
                                .width(columnWidths[colIndex])
                                .padding(12.dp)
                        ) {
                            Text(
                                text = cellText,
                                color = if (isHeader) accentTextColor() else secondaryTextColor(),
                                fontWeight = if (isHeader || colIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                
                if (rowIndex < data.lastIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(secondaryTextColor().copy(alpha = 0.3f))
                    )
                }
                }
            }
        }
    }
}
