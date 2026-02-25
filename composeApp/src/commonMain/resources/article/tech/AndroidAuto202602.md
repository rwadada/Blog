# Androidエンジニアが愛車と通信したくてAndroid Autoで遊んでみた話

コードを書くのと同じくらいには**車に乗るのが好き**で。休みの日はよく愛車で出かけています。
この時期だと週末の度にスキー場行ったりとか。

そんな車に自分で作ったアプリとか載せられたりしたら面白いよな、そういえば、最近の車の車載システムって、中身Androidのことが多いらしいやん？   
ということは、自分でアプリ作って車で動かせるのでは…！？

というわけでやってみたって記録の共有です。
## 意気揚々と調べた結果、いきなりの挫折

車載のAndroidといえば、「**Android Automotive OS**」ですよね。 「よしよし、うちの子のシステムもきっとAndroid Automotiveベースだろうから、ちょっと仕様を調べて…」と、愛車の車載器（MBUX）のシステム情報を意気揚々と調べました。

その結果……

**「勝手にAPK（アプリ本体）入れるの無理じゃね？？」**

はい、終わりました。夢、開始5分で散る。

調べてみると、システム自体はAndroidをベースにしている部分もあるようだったんですが、**セキュリティの観点からユーザーが直接APKを追加・インストールすることはガチガチに制限されている**ようでした。
車という命に関わる機械なので、得体のしれない野良アプリを入れられないようになっているのは当然といえば当然ですね。車載器に直接アプリをぶち込む夢はここで断たれました。無念。

## Android Autoという手があるじゃないか

いやいや、待てよと。 車載器に直接アプリをインストールできないなら、「**Android Auto**」を使えばいいじゃない！

Android Autoなら、手持ちのAndroid端末と車を繋いで、画面を車載器に表示させることができます。

調べてみると、[Android公式の車載アプリ開発ドキュメント](https://developer.android.com/training/cars/apps)によると、**Google Playストアでリリースしない（自分だけで楽しむ野良アプリ）前提であれば**、`CarHardwareManager`**などのAPIを使って車の色々なセンサーデータ（車速や走行距離など）を取得して表示するアプリも作れそう**だということが分かりました。（注:現在、Playストアで一般公開できるAndroid Autoアプリは、ナビゲーションやメディア、IoT制御など一部のカテゴリに厳しく制限されているようです）
ターゲットをAndroid Autoに切り替えて開発スタートです。

## ちょっと解説：Android Automotive と Android Auto の違い

ここで「どっちもAndroidやん、何が違うと？」という方のために、ざっくり解説しておきます。（ざっと調べたので不正確だったらごめんなさい）

%%%_COMPOSABLE_INJECT_SLOT_%%%

つまり、**Android Automotive**は「**車自体がAndroidスマホになる**」のに対し、**Android Auto**は「**車の画面をスマホの外部モニターとして使う**」というイメージですね。

%%%_COMPOSABLE_INJECT_SLOT_%%%

## 具体的にどんなデータが取れるの？
「車の画面にスマホの映像を映すだけ」と思いきや、Android Autoのライブラリ（`CarHardwareManager`）を使えば、車側のハードウェアデータにアクセスできます。

公式ドキュメントを覗いてみると、以下のようなデータが取得できるようです。（注:ただし、実際に取得できるデータは**車種やメーカーの対応状況に大きく依存します**）

%%%_COMPOSABLE_INJECT_SLOT_%%%

## さっそく作ってみる：コード解説

今回は、車のデータ（例えば車速データなど）を取得して、画面に表示するだけのシンプルなアプリを作ろうとしました。 

### 1. 依存関係の追加

まずは `build.gradle.kts` に Car App Library を追加します。

```groovy
dependencies {
    // Car App Library
    implementation("androidx.car.app:app:1.7.0")
    // 車のハードウェアデータ（車速など）にアクセスするため
    implementation("androidx.car.app:app-projected:1.7.0")
}
```

### 2. CarAppServiceの作成

Android Autoから起動されるエントリーポイントです。ここで最初に表示する `Screen` を返します。
Activityとかは無くてOKです。

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

実際のUIとロジックです。Android Autoは普通のAndroidアプリとは異なり、安全のため自由なUIは作れず、決められたTemplate（例では `PaneTemplate`）を使います。 `CarHardwareManager` を使って車のデータにアクセスします。
他にもいくつかテンプレートはあるので調べてみると面白いと思います。

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

まずはAndroid Studioの「Desktop Head Unit (DHU)」というエミュレータで動かし、いけるはず〜とダミーデータを送信して確認してみたところ、、、あれ？ちょっと予想と違うぞ？

![Desktop Head Unitでの実行の様子](images/android_auto_dhu_screenshot_new.png)

権限不足っぽい？
まあ、エミュレータだからきっとうまくハードウェアのダミーデータが取れてないだけだろう！とか考えてました。

## いざ、実機で動かしてみる！

「いや、きっと実車に繋げばちゃんと動いてくれるはず……！」とわずかな望みをかけ、いよいよ車にスマホを繋いでみます。

……と、ここで一つ問題で、 実は自分の愛車、普段はAndroid Autoのオプションを有効化してないんです。

「自分の車で試せんやん！」と焦ったのですが、**すごくちょうど良いことに、車の点検のタイミングで代車として「Android Auto有効化済みのGLC」をお借りすることができました！**

メルセデス様、ありがとうございます。 早速、借りたばかりのGLCのコックピットに乗り込み、BlueToothで接続...。

……が、ランチャー（アプリ一覧）に自分のアプリが見当たらない！？  
（これの理由は後述の「実車ランチャーの壁」を参照）

そこで、どうにかこうにか対応して起動（2回目の接続）を試みたところ……

%%%_COMPOSABLE_INJECT_SLOT_%%%

**「なんか知らんが予期せぬエラーで即死した…」**

気を取り直して、もう一度DHUで試してログを見てみたところ、HostValidatorが無邪気すぎるのが原因と分かったので修正し、再度繋いでみると（3回目の接続）、今度こそ画面が出た！

%%%_COMPOSABLE_INJECT_SLOT_%%%

GLCのディスプレイに自作アプリが表示され、少し前進すると車速データがリアルタイムに動いている……はずが、ずっと「権限エラー...」
あーやっぱりエミュレータの問題じゃなかったのね、と

改めて[公式ドキュメント（Car Hardware API）](https://developer.android.com/training/cars/apps/library/car-hardware-api?hl=ja) をちゃんと読んでみると、「リクエストするべき権限が一部違っていた」ということに気づきました。
実際設定しないといけなかったのはこの辺りです。
```xml
    <uses-permission android:name="com.google.android.gms.permission.CAR_FUEL" />
    <uses-permission android:name="com.google.android.gms.permission.CAR_SPEED" />
    <uses-permission android:name="com.google.android.gms.permission.CAR_MILEAGE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

ちょこちょこと権限周りのコードを修正し、ホイッと再度車に繋いでみた（4回目の接続）結果がこちらです！

%%%_COMPOSABLE_INJECT_SLOT_%%%

**データ取れてるー！！！**

権限問題をクリアしたことで、無事にいくつかの生データが取れるようになりました！わーい！

ただ、値が表示されずに「なし（空）」になる項目もまだ一部残っています。これに関しては、そもそも車側が連携非対応のデータなのか、あるいは実際に走行して速度などを出してみないと検知されないデータなのかまでは（停車時の検証のため）確認できませんでしたが、ひとまず実車からリアルタイムにデータが取れたという事実で一旦大満足です！
動画中で一部の表示が切り替わる動きをしているところは、ギアをドライブに入れたタイミングで変わっていて、車載システムならではの表現なんだろうなとちょっとだけ感動してたりしました。

### 実車ランチャーの壁
DHU（エミュレータ）では問題なく表示されていたアプリが、**いざ実車に繋ぐと「Android Autoのランチャー（アプリアイコン一覧）」から忽然と消え去る**動きをします。スマホ側で「提供元不明のアプリ」を許可してもダメ。Activityがないから？いや違う。

原因は、Googleが実車に対して行っている**セキュリティブロック**のようでした。
現在のAndroid Autoは、単に「Playストアからインストールされたか」というラベルだけでなく、**そのアプリ自体が本当にGoogle Play Consoleに登録され、署名付きで配信されたものか**を厳密にチェックしています（DHUは開発用なのでこの照合をスキップしてくれています）。
危険度を考えたらある意味当たり前のことかもしれないですが、結構普段のAndroidアプリ開発とのギャップに驚きました。

**【解決策：内部テストトラックへのアップロード】**

1. Releaseビルドを作成し、自前のKeystoreで署名する（Debugビルドは不可）
2. **Google Play Console**（開発者アカウント。初回登録料25ドルのお布施が必要）にアプリとして登録する
3. ビルドしたアプリを **内部テスト（Internal Testing）** トラックにアップロードする
4. テスターとして自分を登録し、スマホのGoogle Playストアから「内部テスト版」としてダウンロード＆インストールする

これでようやく、Android Auto様が「あ、これは安全チェック（少なくとも開発者自身のテスト）を通った署名付きアプリだね」と認めてくれ、実車のランチャーにアイコンが出現しました。

## まとめ：IoTって楽しいよね

最初は「APK入れられないやんけ！」という絶望から始まりましたが、結果的にAndroid Auto経由で車と通信することができました。（代車のGLCのおかげですが笑）

今回は単純なデータを表示するだけでしたが、これを応用すれば：

- 自分好みのダッシュボードを作る
- 特定の場所（家や職場）に着いたら自動で何かAPIを叩く
- アクセル開度や燃費などの状態をロギングして後から分析する（注:取得できるデータは車種によります）


などなど、**夢があるなーと**

普段のスマホアプリ開発も楽しいですが、車のようなリアルな物理デバイスと繋がるIoT（Internet of Things）的な開発は、また違った面白さがありました！ 自分の車でもAndroid Autoを使えるようにしたくなりました。（ちょっと高いからね、有効化悩み中です）

ぜひ休日の工作として「Android Autoでのオレオレアプリ開発」、試してみてください。オススメです！

それと、このあと調べてたらもう一個わかったんですが、メルセデスに関しては、実はAPI経由で車のデータが取れそうということ。
メルセデス・ベンツは開発者向けポータル[「Mercedes-Benz /developer」](https://developer.mercedes-benz.com/)を公開しています。 しかも「**BYOC (Bring Your Own Car)**」という仕組みを使えば、個人開発者でもAPI経由で自分の車の走行距離やドアの開閉状態などのデータをクラウドから取得できるようです。すごい！ 今回は「車載ディスプレイに自分のUIを出したい」という目的だったので使ってないですが、いずれこのAPIも遊んでみたいです。
