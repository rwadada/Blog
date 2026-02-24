# Androidエンジニアが愛車と通信したくてAndroid Autoで遊んでみた話

実はコードを書くのと同じくらい**車に乗るのが大好き**で。休みの日はよく愛車で出かけています。

ある日、ふと思いました。

**「最近の車の車載システムって、中身Androidのことが多いらしいやん？ ということは、自分でアプリ作って車で動かせるっちゃね…！？」**

やってみる！（と言いつつ、ずっと面倒でやってなかったんですが、このブログ書こうかな〜と思ったのでついでにって感じですw）

## 意気揚々と調べた結果、いきなりの絶望

車載のAndroidといえば、「**Android Automotive OS**」ですよね。 「よしよし、うちのメルセデスのシステムもきっとAndroid Automotiveベースだろうから、ちょっと仕様を調べて…」と、愛車の車載器（MBUX）のシステム情報を意気揚々と調べました。

その結果……

**＿人人人人人人人人人人＿** **＞　APK入れるん無理じゃね？？　＜** **￣Y^Y^Y^Y^Y^Y^Y^Y^￣**

はい、終わりました。夢、開始5分で散る。

調べてみると、システム自体はAndroidをベースにしている部分もあるようなのですが、**セキュリティの観点からユーザーが直接APK（アプリの本体ファイル）を追加・インストールすることはガチガチに制限されている**ようでした。
車という命を預かる乗り物なので、得体のしれない野良アプリを入れられないようになっているのは当然といえば当然ですね。車載器に直接アプリをぶち込む夢はここで断たれました。つらい。

> **【補足】実はAPI経由で車のデータは取れる！** 色々調べていて気づいたんですが、メルセデス・ベンツは開発者向けポータル[「Mercedes-Benz /developer」](https://developer.mercedes-benz.com/ "null")を公開しています。 しかも「**BYOC (Bring Your Own Car)**」という仕組みを使えば、個人開発者でもAPI経由で自分の車の走行距離やドアの開閉状態などのデータをクラウドから取得できるようです。すごい！ 今回は「車載ディスプレイに自分のUIを出したい」という目的だったので使ってないですが、いずれこのAPIも遊んでみたいです。

## Android Autoという手があるじゃないか

いやいや、待てよと。 車載器に直接アプリをインストールできないなら、「**Android Auto**」を使えばいいじゃない！

Android Autoなら、手持ちのAndroidスマホと車を繋いで、画面を車載器に表示させることができます。

しかも調べてみると、[Android公式の車載アプリ開発ドキュメント](https://developer.android.com/training/cars/apps)によると、**Google Playストアでリリースしない（自分だけで楽しむ野良アプリ）前提であれば**、`CarHardwareManager`**などのAPIを使って車の色々なセンサーデータ（車速や走行距離など）を取得して表示するアプリも作れそう**だということが分かりました。（注:現在、Playストアで一般公開できるAndroid Autoアプリは、ナビゲーションやメディア、IoT制御など一部のカテゴリに厳しく制限されています）
ターゲットをAndroid Autoに切り替えて開発スタートです。

## ちょっと解説：Android Automotive と Android Auto の違い

ここで「どっちもAndroidやん、何が違うとね？」という方のために、ざっくり解説しておきます。（ざっと調べたので不正確だったらごめんなさい）

%%%_COMPOSABLE_INJECT_SLOT_%%%

つまり、**Android Automotive**は「**車が巨大なAndroidスマホになる**」のに対し、**Android Auto**は「**車の画面をスマホの外部モニターとして使う**」というイメージですね。

%%%_COMPOSABLE_INJECT_SLOT_%%%

## 具体的にどんなデータが取れるの？
「車の画面にスマホの映像を映すだけ」と思いきや、Android Autoのライブラリ（`CarHardwareManager`）を使うと、車側の様々なハードウェアデータにアクセスできます。

公式ドキュメントを覗いてみると、以下のようなデータが取得できるようです。（注:ただし、実際に取得できるデータは**車種やメーカーの対応状況に大きく依存します**）

%%%_COMPOSABLE_INJECT_SLOT_%%%

## さっそく作ってみる：コード解説

今回は、車のデータ（例えば車速データなど）を取得して、画面に表示するだけのシンプルなアプリを作ります。 

### 1. 依存関係の追加

まずは `build.gradle.kts` に Car App Library を追加します。

```groovy
dependencies {
    // Car App Library
    implementation("androidx.car.app:app:1.4.0")
    // 車のハードウェアデータ（車速など）にアクセスするため
    implementation("androidx.car.app:app-projected:1.4.0")
}
```

### 2. CarAppServiceの作成

Android Autoから起動されるエントリーポイントです。ここで最初に表示する `Screen` を返します。

```kotlin
class MyCarAppService : CarAppService() {
    override fun createHostValidator(): HostValidator {
        // 野良アプリで動かすため、とりあえず全てのホストを許可（リリース時はNG）
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(): Session {
        return object : Session() {
            override fun onCreateScreen(intent: Intent): Screen {
                return MyCarScreen(carContext)
            }
        }
    }
}
```

### 3. Screenの作成とデータ取得

実際のUIとロジックです。Android Autoは安全のため自由なUIは作れず、決められたTemplate（今回は `PaneTemplate`）を使います。 `CarHardwareManager` を使って車のデータにアクセスします。

```kotlin
class MyCarScreen(carContext: CarContext) : Screen(carContext) {
    private var speedString = "取得中..."

    init {
        // 車のハードウェア情報にアクセス
        val carHardwareManager = carContext.getCarService(CarHardwareManager::class.java)
        val carInfo = carHardwareManager.carInfo

        // 車速の監視（注:別途マニフェスト等でパーミッションの宣言・許可が必要）
        carInfo.addSpeedListener(ContextCompat.getMainExecutor(carContext)) { speed ->
            val rawSpeed = speed.rawSpeedMetersPerSecond.value
            if (rawSpeed != null) {
                // m/s から km/h に変換
                val kmh = rawSpeed * 3.6
                speedString = String.format("%.1f km/h", kmh)
                // UIを更新
                invalidate()
            }
        }
    }

    override fun onGetTemplate(): Template {
        val row = Row.Builder()
            .setTitle("現在の車速")
            .addText(speedString)
            .build()

        val pane = Pane.Builder()
            .addRow(row)
            .build()

        return PaneTemplate.Builder(pane)
            .setHeaderAction(Action.APP_ICON)
            .setTitle("AndroidAuto デモアプリ")
            .build()
    }
}
```

まずはAndroid Studioの「Desktop Head Unit (DHU)」というエミュレータで動かし、ダミーデータで画面が表示されることを確認しました。

%%%_COMPOSABLE_INJECT_SLOT_%%%

## いざ、実機で動かしてみる！

エミュレータで動くことが確認できたので、いよいよ車にスマホを繋いでみます。

……と、ここで一つ問題が。 実は自分の愛車、普段はAndroid Autoのオプションを有効化してないとです（ナビは純正で満足していたので…）。

「自分の車で試せんやん！」と焦ったのですが、**なんと今回、ちょうど車の点検のタイミングで代車として「Android Auto有効化済みのGLC」をお借りすることができました！**

**これは完全に運命。** メルセデス様、ありがとうございます。 早速、借りたばかりのGLCのコックピットに乗り込み、BlueToothで接続...。

自分で書いたコードが、GLCの美しい大型ディスプレイに映し出される瞬間……緊張します。

%%%_COMPOSABLE_INJECT_SLOT_%%%

> 注:ブログの読者へ：動画で、GLCのディスプレイに自作アプリが表示され、少し前進すると車速データがリアルタイムに動いている様子を見てください！

**うおおおお！！ 動いたーーーー！！！**

普段PCの画面やスマホの小さな画面で動かしている自分のロジックが、車という数トンの巨大なハードウェアと連携して動くのは、めちゃくちゃ感動します。

## まとめ：IoTって楽しいよね

最初は「APK入れられないやんけ！」という絶望から始まりましたが、結果的にAndroid Auto経由で車と通信することができました。（代車のGLCのおかげですが笑）

今回は単純な車速を表示するだけでしたが、これを応用すれば：

- 自分好みの最強のダッシュボードを作る

- 特定の場所（家や職場）に着いたら自動で何かAPIを叩く

- アクセル開度や燃費などの状態をロギングして後から分析する（注:取得できるデータは車種によります）


などなど、**夢がめちゃくちゃ広がります。**

普段のスマホアプリ開発も楽しいですが、車のようなリアルな物理デバイスと繋がるIoT（Internet of Things）的な開発は、また違った面白さがありました！ 自分の車でもAndroid Autoを使えるようにしたくなりました。（ちょっと高いからね、有効化悩み中です）

ぜひ休日の工作として「Android Autoでのオレオレアプリ開発」、試してみてください。オススメです！