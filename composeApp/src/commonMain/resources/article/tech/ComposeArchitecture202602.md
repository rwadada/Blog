# 個人的に思うJetpackComposeにおける画面構成のパターンについて

ようやく自分の開発に関わっているプロダクトだったり、個人で開発してるアプリだったりでComposeで完結するような構成となることが増えてきました。  
いくつかのプロジェクトを通して見えてきてるこのパターン良かったなをメモ的に残しておきます。

> 前提事項
> 本記事では、単一の画面（Screen）内の構成にフォーカスします。Navigationなどの「画面遷移」の仕組みについてはスコープ外とします。

## 1. Compose時代、ViewModelの存在意義って？

Composeには `remember` や `mutableStateOf` といった強力な状態管理の仕組みがあります。  
SavedStateHandleを組み合わせて使えば、「これだけで十分では？」と思ってしまう時期が自分にもありました。
確かに、そういったPresenterクラスを作って `CompositionLocal` 等で画面全体に配り歩くアプローチも技術的には可能です。
しかし実際にやろうとすると、プロセスデスからの復帰、ライフサイクルに合わせたコルーチンのキャンセル、DIライブラリとの連携など、
Android特有の泥臭い部分を自前で完璧にハンドリングする必要が出てきます。

「そんな車輪の再発明やバグの温床に悩むより、素直にGoogleが用意しているエコシステムである `ViewModel`
に任せるのが、依然として実用上の最善手である」というのが、現時点での結論です。
Compose内の `remember` は、あくまで「UIの表示に係る一時的な状態（例：TextFieldの入力途中の値、アコーディオンの開閉状態）」を管理するのに適しています。

一方で、ViewModelは以下の役割を担います。

1. **ビジネスロジックとUIの分離**: DBからのデータ取得やAPI通信等、UIに依存しない処理の起点となる。
2. **画面回転/構成変更時の状態保持**: 端末の回転などでActivityが破棄されても、ViewModelは生存し続けるので、再取得のコストは減る。
3. **テスト容易性**: UIフレームワークから切り離されるので、純粋なUTが書きやすい。

Compose時代においても、ViewModelは「UIに表示すべき最終的なデータ（状態）を保持し、提供する」という重要な司令塔の役割を果たします。

## 2. 状態管理の要：UiStateのあり方（Sealed InterfaceとCombine）

ViewModelがUIに状態を伝える際、個別の`StateFlow`を複数定義する（例：`isLoading`, `userName`, `errorMessage`
など）のは推奨されません。状態の不整合が起きやすくなるためです。  
AndroidView時代に「画面の内容は変わってるのに、内部状態が追いついてない」「一緒に出るはずがない情報が同時に表示されてしまった」といったバグに悩まされた経験は、きっと誰にでもあるのではないでしょうか。

より堅牢でComposeと相性が良いのは、`sealed interface`を用いて画面の取りうる状態を排他的に定義し、複数のデータの流れから宣言的に
`UiState`を合成するアプローチです。

まず、画面の状態を定義します。

```kotlin
sealed interface UserProfileUiState {
    data object Loading : UserProfileUiState
    data class Success(
        val user: User,
        val isEditing: Boolean // ユーザー入力などの付加的な状態
    ) : UserProfileUiState
    data class Failure(val errorMessage: String) : UserProfileUiState
}
```

次に、ViewModel内でデータの取得結果と、ユーザーの入力状態などを`combine`オペレータを使って結合し、最終的な`UiState`を算出します。

```kotlin
class UserProfileViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    // 1. データ取得のFlow (例: DBやAPIから)
    private val fetchedData: Flow<Result<User>> = getUserUseCase()

    // 2. ユーザーの操作によるローカルな状態
    private val isEditing = MutableStateFlow(false)

    // 3. 複数の状態を合成して、1つのUiStateを作る
    val uiState: StateFlow<UserProfileUiState> = combine(
        fetchedData,
        isEditing
    ) { result, editing ->
        result.fold(
            onSuccess = { user ->
                UserProfileUiState.Success(user, editing)
            },
            onFailure = { error ->
                UserProfileUiState.Failure(error.message ?: "Unknown Error")
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserProfileUiState.Loading
    )

    // ...
}
```

Compose側ではこれを収集（collect）し、when 式で安全にUIを出し分けます。

```kotlin
@Composable
fun UserProfileContent(viewModel: UserProfileViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UserProfileUiState.Loading -> LoadingIndicator()
        is UserProfileUiState.Failure -> ErrorMessage(uiState.errorMessage)
        is UserProfileUiState.Success -> {
            UserDetail(
                user = uiState.user,
                isEditing = uiState.isEditing
            )
        }
    }
}
```

このアプローチの最大のメリットは、「Loading状態なのにデータが存在する」といった矛盾した状態が決して発生しないことと、データの変更に応じて自動的かつ宣言的にUiStateが再構築されることです。これはComposeの思想に完璧にマッチします。  
「じゃあ、Loading中やError時でも表示したおきたいデータ（例：画面タイトルなど）がある場合どうするの？」となるかもしれません。そういった場合は、以下のように状態をネストさせることで柔軟に対応可能です。

```kotlin
data class UserProfileUiState(
    val pageTitle: String,
    val contentState: ContentState
) {
    sealed interface ContentState {
        data object Loading : ContentState
        data class Success(
            val user: User,
            val isEditing: Boolean // ユーザー入力などの付加的な状態
        ) : ContentState
        data class Failure(val errorMessage: String) : ContentState
    }
}
```

状態をネストさせるとデータの更新が少し面倒になるというデメリットもあります。その場合は、`ArrowOptics`
などのライブラリを導入して更新を簡略化するアプローチは取れると思います。

また、そもそもとしてUiStateはあくまで「UI表示のためのデータ」であるので、ドメインモデル（今回の例だと`User`）をそのまま持たせるのではなく、
UIが必要とするプリミティブな型にマッピングしてからUiStateに持たせるようにすると、よりUI層の責務が明確になるのでおすすめです。  
その場合はUiStateの`Companion object`に変換用の`from`関数をつけたりすることもままあります。

## 3. Composable関数の責務分割（Route / Screen / Content）

状態管理の形が決まったところで、次はそれを描画するComposable関数自体の構成についてです。
1つの巨大なComposable関数にすべてを詰め込むと、ViewModelへの依存が強くなりすぎてプレビュー（`@Preview`
）が描画できなくなったり、再利用性が低下したりします。

個人的には、画面を構成するComposable関数をベースとなる**3つのレイヤー**（Route / Screen / Content）に分け、さらに実運用ではそれを
**Section**と**Component**に細分化するパターンを採用しています。

### 基本の3レイヤー

1. **Route** (例: `UserProfileRoute`)
    - **役割**: Navigationのグラフ等から直接呼ばれるエントリーポイント。
    - **責務**: ViewModelの取得（DI）、`UiState`
      のcollect、ViewModelのメソッド呼び出しなどを行います。UIの具体的な描画（Scaffoldなど）は持ちません。いわゆる「Stateful」なコンポーネントです。
2. **Screen** (例: `UserProfileScreen`)
    - **役割**: 画面の全体レイアウト（ガワ）を定義する層。
    - **責務**: `Scaffold`を持ち、`TopAppBar`などの配置を決めます。ViewModelは受け取らず、純粋にデータ（`UiState`）を受け取るため、
      **ここから安全にプレビュー**（`@Preview`）を書くことができます（Stateless）。
3. **Content**: (例: `UserProfileContent`)
    - **役割**: 画面のメインコンテンツを描画する層。
    - **責務**: `Scaffold`の`content`スロットに入るUIを構築し、状態（Loading/Success/Failure）に応じた出し分けを行います。

### 実運用でのパッケージ構成（SectionとComponentの追加）

しかし、複雑な画面になると`Content`の中にすべてのUI要素を直接書くのは辛くなってきます。そこで、実際のプロジェクトでは以下のようなパッケージ構成で運用しています。

%%%_COMPOSABLE_INJECT_SLOT_%%%

- **Section**: `Content`の中身を構成する「意味のある機能の塊」です。例えば「プロフィールヘッダー部分」「最近のアクティビティリスト部分」などに分割します。これにより
  `Content`自体は「Sectionを縦に並べるだけのシンプルなレイアウト」になり、見通しが格段に良くなります。
- **Component**: そのFeature（画面）の中だけで使う、細かなUI部品です。アプリ全体で使い回す共通UIコンポーネント（デザインシステムのCore
  Componentsなど）とは明確に分け、このFeature内に閉じて配置します。

このように「Route -> Screen -> Content -> Section ->
Component」と段階的に分割することで、各Composableの責務が小さくなり、メンテナンス性とプレビューのしやすさが劇的に向上します。

## 4. 引数バケツリレーからの脱却：Actionsパターン

前章で関数を綺麗に分割できましたが、次に問題になるのが「ユーザーのアクション（イベント）の伝達」です。

Composeでは、状態を下に流し、イベントを上に上げる（State down, Events
up）のが基本です。ViewModelの関数を直接ScreenやContentに渡すのではなく、コールバック（ラムダ）を引数として渡すことが推奨されます。

しかし、前述のように**Section**や**Component**まで階層が深くなると、このコールバックパラメータが爆発的に増える問題が発生します。

```kotlin
// 悪い例：引数が多すぎる
@Composable
fun UserProfileContent(
    uiState: UserProfileUiState.Success,
    onRefreshClick: () -> Unit,
    onEditNameClick: (String) -> Unit,
    onFollowClick: () -> Unit,
    onBlockClick: () -> Unit,
    // ... これをさらにSectionにもバケツリレーする羽目に...
) {
    ...
}
```

プレビューを書くのも一苦労ですし、何層にもわたって引数を渡す「バケツリレー」が発生してしまいます。

これを解決するのが **Actionsパターン** です。(勝手に命名してます。)
画面から発生するアクションをまとめたインターフェースやデータクラスを作成します。

```kotlin
interface UserProfileActions {
    fun onRefreshClick()
    fun onEditNameClick(newName: String)
    fun onFollowClick()
    fun onBlockClick()
}
```

そして、Composable関数（Screen, Content, Sectionなど）の引数をこの Actions ひとつにまとめます。

```kotlin
@Composable
fun UserProfileContent(
    uiState: UserProfileUiState.Success,
    actions: UserProfileActions
) {
    // Content内からさらにSectionへactionsを渡すのも1つの引数で済む
    UserProfileHeaderSection(user = uiState.user, actions = actions)
}
```

最上位の Route 階層で、ViewModelのメソッドを呼ぶようにActionsを実装し、下層に一括して渡します。  
ViewModelが最初からActionsを実装するという手もあります。

```kotlin
@Composable
fun UserProfileRoute(viewModel: UserProfileViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val actions = remember(viewModel) {
        object : UserProfileActions {
            override fun onRefreshClick() = viewModel.refresh()
            override fun onEditNameClick(name: String) = viewModel.editName(name)
            override fun onFollowClick() = viewModel.follow()
            override fun onBlockClick() = viewModel.block()
        }
    }

    UserProfileScreen(uiState = uiState, actions = actions)
}
```

これにより、下層のComposableのシグネチャがスッキリし、プレビューを作成する際もダミーのActionsクラス（何もしない空のオブジェクトなど）を渡すだけで済むようになります。

%%%_COMPOSABLE_INJECT_SLOT_%%%

## 5. Fat ViewModelを防ぐ：UseCase層の導入（Stateless & fun interface）

`UiState`と`Actions`でViewModelとCompose間のやり取りは綺麗になりました。しかし、調子に乗ってViewModelにすべての処理を書いていると、すぐにViewModelが数百行〜数千行の巨大なクラス（Fat
ViewModel）になってしまいます。

データの取得、変換、バリデーション、他APIとの連携…。これらの「ビジネスロジック」をViewModelに詰め込むと人智を超えたViewModelが爆誕してしまいます。

ここで登場するのが **UseCase（あるいはInteractor）層** です。UseCaseは「1つの具体的なユースケース（ユーザーがやりたいこと）」を担当します。

ここで個人的に絶対に守りたい方針が2つあります。
それは **「UseCaseは状態を持たない（Stateless）」** ことと、**「fun interface (Functional Interface) として定義する」** ことです。
```kotlin
// 1. fun interface として定義する
fun interface FollowUserUseCase {
    suspend operator fun invoke(userId: String): Result<Unit>
}

// 2. 状態を持たない(Statelessな)実装クラス
class FollowUserUseCaseImpl(
    private val userRepository: UserRepository
) : FollowUserUseCase {
    override suspend operator fun invoke(userId: String): Result<Unit> {
        // フォロー制限のチェックや、リポジトリの呼び出しなど
        // ビジネスロジックをここに書く
        return userRepository.follow(userId)
    }
}
```
ViewModelの役割は、「Actionsを受け取り、適切なUseCaseを呼び出し、その結果を状態（StateFlow）に反映させること」に限定（ルーティングと状態更新）します。
```kotlin
class UserProfileViewModel(
    private val followUserUseCase: FollowUserUseCase,
    // ...
) : ViewModel() {

    fun follow(userId: String) {
        viewModelScope.launch {
            // 例: Follow処理を呼び出し、結果に応じてローカルのFlowを更新するなど
            val result = followUserUseCase(userId)
            
            result.onSuccess {
                // ...
            }.onFailure { error ->
                // ...
            }
        }
    }
}
```
この方針（Stateless + fun interface）を導入することで、以下のようなメリットがあります。
1. **テストが書きやすくなる**: `fun interface`にしておけば、ViewModelのテストを書く際にいちいちモックライブラリ（MockKなど）を使わなくても、`FollowUserUseCase { Result.success(Unit) }`のようにラムダ式（SAM変換）で簡単にフェイクを差し込めます。
2. **バグの温床が減る**: UseCase自体が状態（プロパティ）を持たないことを強制できるため、意図しない状態の不整合やメモリリークを防げます。
3. **ViewModelがシンプルになる**: コードの可読性が上がり、ViewModelのテストは「状態の遷移」のみに集中できます。複雑なロジックのテストはUseCase単体で行えば済みます。

## 6. まとめ
Jetpack Compose時代の画面アーキテクチャは、以下の組み合わせが一つの解となります。
1. **ViewModel**: UIの状態管理の司令塔として引き続き活用する。 
2. **UiState (Sealed Interface + Combine)**: 画面の状態を排他的に定義し、Flowを合成して宣言的にUI状態を構築する。必要に応じてネストやプリミティブ化を検討する。 
3. **Route / Screen / Content (+ Section / Component) 分割**: Composableの責務とパッケージを分け、ViewModel依存を切り離すことでメンテ性とプレビューを容易にする。 
4. **Actionsパターン**: 深い階層（Section等）へのイベントバケツリレーを防ぐ。 
5. **UseCase (Stateless + fun interface)**: 状態を持たない関数インターフェースとしてビジネスロジックを抽出し、Fat ViewModelを防ぎつつテストを容易にする。

これらのパターンを導入することで、Composeの宣言的な強みを最大限に活かしつつ、変更に強く、テストしやすいAndroidアプリを構築することができるんじゃなかろうかと今のところは感じてます。

移行フェーズにあるプロジェクトの参考になれば幸いです！