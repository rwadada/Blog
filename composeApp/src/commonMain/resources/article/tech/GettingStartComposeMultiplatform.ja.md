# FleetとCompose Multiplatformで始めるマルチプラットフォーム開発
今回はFleetという新しいIDEを使ってComposeMultiplatformを使ったアプリを作成する方法を紹介

## Compose Multiplatformとは？
（何度か紹介してる気がしますが）  
**複数のプラットフォーム間で UI を共有できる宣言的フレームワーク**でAndroid、iOS、デスクトップ、ウェブ用共有 UI を開発できるものです。　　
<img width="1217" alt="スクリーンショット 2024-09-04 0 46 03" src="https://github.com/user-attachments/assets/d2ae5453-67fb-4e30-81d5-25b6228f1ebf">

- あれ？KMP（KotlinMultiplatform）って無かったっけ？と思った方向け  
  - ComposeMultiplatformはKotlinMultiplatform上に構築されたUIフレームワークです。  
つまりKMP > ComposeMultiplatform

ComposeMultiplatformを利用することで、最近は主流となってきているJetpackComposeを使ったAndroidアプリ開発の知識で複数のプラットフォームに適用可能なアプリを作成可能となります。

## Fleetとは？
JetBrainsが出しているIDEの一つです：https://www.jetbrains.com/ja-jp/fleet/  
現在パブリックプレビュー期間のため無料で使えます。  

ざっくり言うと、JetBrainsが提供しているIDEを統合して良い感じに使えるようにしてみたんだけどどうよ？ってIDEになってます。  
今回このIDEを使っているのは、AndroidStudioと異なり、Androidターゲット以外を指定していた場合でもComposeのPreviewが見れる点（後述）とKMPをデフォルトでサポートしているため。

## ComposeMultiplatformなアプリを作っていく。
### プロジェクトの初期設定
出た当初に比べてめちゃくちゃ楽になりました！当初はサンプルリポジトリを見て自分で構成を真似して作成といった対応でした。  
現在はこんなものがあります。  https://kmp.jetbrains.com/
<img width="829" alt="スクリーンショット 2024-09-04 1 03 18" src="https://github.com/user-attachments/assets/bc3ff605-085c-4e78-9b82-f8ff33cce9be">  

#### 素晴らしいポイント
- Android, iOS, Desktop, Web, Serverの選択肢から自分が好きなものを選んでプロジェクトのテンプレートを作成可能
  - 作成されたテンプレートはZIPでダウンロードされるので回答してビルドすれば、環境構築さえ終わっていればすぐ確認可能
- iOSについてはComposeのUIを使うのかSwiftUIを使うのか選択可能
- プロジェクト管理ツールに[Amper](https://blog.jetbrains.com/ja/amper/)を使うテンプレートも利用可能 

### ビルド
Fleetのダウンロードとプロジェクトテンプレートのダウンロードが終わったらビルドしてみます。  
Fleetを初めて開くとこんな感じ
<img width="1392" alt="Screenshot 2024-09-05 at 12 12 57" src="https://github.com/user-attachments/assets/88838915-0184-4e26-abe7-b1224c1a1c6e">
ダウンロードしたプロジェクトを開いて、解析が終わるのを待ちます（2~3分ほどはかかるはず）  　
解析が終わるとこういった形で、何が不足してるよ〜というのを一覧で表示してくれるので、それに従い設定します。  
※全てがOKにならないとFleetの実行機能を利用しての実行が出来ないためサボらず全部設定します。  
※ここで出てくるエラーめちゃくちゃ不親切なので、全ツールの事ある程度触ったこと無いと意外と時間かかります。（Issueも出てる：https://youtrack.jetbrains.com/issue/FL-26508/Preflight-check-Gradle-plugins-are-not-found-is-not-helpful）
<img width="353" alt="Screenshot 2024-09-05 at 12 18 25" src="https://github.com/user-attachments/assets/5f650244-6377-4761-b59d-0ce94d852224">

設定完了後⌘+Rで実行ターゲットを選ぶ画面が表示されます。
<img width="673" alt="Screenshot 2024-09-05 at 13 27 41" src="https://github.com/user-attachments/assets/4ec6ffcd-ce02-4947-b3bb-325ab028a89d">
実行後はこんな感じ
ちゃんと全部動きます。

### 構成の詳細
<img width="350" alt="Screenshot 2024-09-05 at 14 06 26" src="https://github.com/user-attachments/assets/03283efb-1a9d-437e-9847-1496ff3df25e">

#### composeApp
ここが基本的に実態になっています。
この配下に以下のプラットフォームごとのディレクトリが作成されます。
- commonMain
    - 全プラットフォーム共通の処理とそれぞれをまとめるInterfaceが格納される
- androidMain
    - Androidに特化した設定や実装が格納される
- desktopMain
    - Desktopアプリに特化した設定や実装が格納される
- iosMain
    - iOSに特化した設定や実装が格納される
#### iosApp
よくあるxcdeprojとかが入っています。  
内容としてはこんな感じで、Composeで定義した画面を呼んでいるという非常に薄い内容になっています。  
<img width="735" alt="Screenshot 2024-09-05 at 14 16 38" src="https://github.com/user-attachments/assets/9f82adfe-a7f0-4992-96b7-36cf384872a5">

#### 設定ファイル系
以前のKMPを知っている方は感動するくらい簡略化されてる気がします。
どのプラットフォームにアプリを適用するのか、どんなライブラリを使うのかを定義出来ます。
build.gradke.kts
```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}

```

他の部分気になる方はサンプルリポジトリ置いておくのでどうぞ　　
https://github.com/rwadada/TaskRecorder

### UIの構築
commonMainに含まれるComposeで定義した物が全プラットフォームに配布されます。
```kotlin
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}
```

良いや、実装ベースに話します

それと、ここでポイントになってくるのがFleetでのPreviewです。  
従来のComposeのPreviewはAndroidフレームワークに紐づいているため、Androidターゲットのものでない限り表示不可能でした。  
ただし、FleetはIDEとしてPreviewをサポートしています。(`org.jetbrains.compose.ui.tooling.preview.Preview`)  
これによってcommonにいてもPreviewの確認が可能となっています。  
ただし、、、めちゃくちゃ重いので現状まともに使えません、直接確認したい画面に遷移できる導線を用意して、普通に実行したほうが早いです

### 状態の管理
Androidアプリを使っているかたはみんなご存知[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ja)を使って構築可能です
なぜか→なんとAndroidのViewModelがマルチプラットフォームされたから
https://developer.android.com/jetpack/androidx/releases/lifecycle?s=09#2.8.0-alpha03
> The lifecycle-viewmodel artifact and APIs like ViewModel, ViewModelStore, ViewModelStoreOwner, and ViewModelProvider are now shipped in artifacts compatible with Kotlin Multiplatform. (b/214568825)

ので、AndroidのViewModelを使って実装が可能となっています。
ただ元々がAndroidなのでcommonの実装なのにimport文にandroidというフレーズがでてくるという残念ポイントがあったり

また、Activityなどを使っていないためLifecycleが想定と異なり、意図しないタイミングでデータが初期化されてしまったり、逆に引き継がれたりとAndroidをずっとやってきた人には罠となり得る挙動をすることが多々あるため必ずしもAndroidエンジニアにとってフレンドリーかというとそうとは言い切れない状態になっています。
（逆にComposeに慣れていれば割と素直な挙動（rememberを使った場合の挙動）になるので最近のAndroidエンジニアには優しいのかも）

### Debug
実演

### その他
いい感じに
