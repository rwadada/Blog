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

### 素晴らしいポイント
- 
