# Android Base 기본 설정

### WARNING

> - **Kotlin DSL**의 사용을 위해 **Android Studio Bumblebee(2021.1.1)** 버전 이상이 필요합니다.<br>
> - **ktlint**의 사용을 위해 **JAVA 11** 버전 이상의 **환경변수** 설정이 필요합니다.<br>
    > **Mac** : `export JAVA_HOME=/Applications/Android\\ Studio.app/Contents/jre/Contents/HOME/` <br>
    > **Window** : `SET JAVA_HOME=C:\Program Files\Android\Android Studio\jre`
>
> - ❌ string, drawable과 같은 리소스 파일은 모듈간 공유해서는 안됩니다. <br> ex) presentation 모듈의 R.string.common_confirm을 하위 모듈인 presentation:splash 에서 사용하는 경우

<br>


<details>
<summary><h3>🛠 Project 기본 세팅</h3></summary>

- `package`명 변경
- `:buildSrc`의 `ProjectConfiguration`에서 프로젝트 환경 설정
- `gradle.properties`의 `api.url`, `socket.url` 수정

</details>

<details>
<summary><h3>📌 모듈 추가</h3></summary>

- 모듈의 이름은 **소문자로 시작하는 한단어**로 하되, 불가피한 경우 언더바(_)의 사용이 아닌 **Camel-case**를 사용합니다.
- 라이브러리 의존성 추가 시 필요한 모듈에만 추가하며, 더 이상 사용하지 않는 의존성은 삭제합니다.
- `Presentation layer`에서 사용되는 안드로이드 리소스는 사용될 모듈에 정확하게 추가합니다.<br>
  → 공통으로 사용되는 리소스라고 하여 Presentation 모듈의 res에 추가하는 것이 아닌, 각각의 모듈에 추가, <br>
  → 또한 Presentation 모듈에는 BaseActivity, BaseFragment 등 기본 abstract 관련 클래스에서만 사용되는 리소스만 추가하길 권장합니다.

</details>

<details>
<summary><h3>📗 Library 추가</h3></summary>


`buildSrc` 모듈의 `Libraries` 참조

```Kotlin
// 예시
// ==========================================================================
// Glide : com.github.bumptech.glide:glide:4.12.0
// Glide-Compiler : com.github.bumptech.glide:compiler:4.12.0
// Glide-Integration : com.github.bumptech.glide:okhttp3-integration:4.11.0
// ==========================================================================
// groupName : com.github.bumptech.glide
// name : glide / compiler / okhttp3-integration
// version : 4.12.0 / 4.12.0 / 4.11.0

object Glide : LibraryGroup(groupName = "com.github.bumptech.glide", version = "4.12.0"), Implementable, Library {
  override val name: String = "glide"   // 기본 베이스가 되는 Glide가 group뿐만이 아닌 라이브러리로써도 사용될 수 있으므로 Library 상속 후 name override

  object Compiler : LibraryGroupChild(group = Glide, name = "compiler"), Kapt
  // 기본 implementation이 아닌 Kapt로 사용됨을 명시함

  object Integration : LibraryGroupChild(group = Glide, name = "okhttp-integration", version = "4.11.0"), Implementable
  // 기본 Implementation이 사용되며 version이 그룹과 다르므로 추가적으로 명시함
}

```

</details>

---

<details>
<summary><h2>Hilt 기본 사용법</h2></summary>

```Kotlin
// 1) Application 클래스에 @HiltAndroidApp 어노테이션 추가
// 2) Activity, Fragment등 안드로이드 진입점에 @AndroidEntryPoint 어노테이션 추가
// 3) ViewModel에 @HiltViewModel 어노테이션 추가
// 4) 주입이 필요한 생성자 혹은 필드에 @Inject 어노테이션 추가
//    - 필드에 의존성을 주입 하는 경우 private이 될 수 없습니다.

```

```Kotlin
// 1) DataSource, Repository와 같이 Interface와 Impl 구현이 따로 존재하여 인터페이스로부터 직접적으로 생성자의 주입이 불가능한 경우
@Module
@InstallIn(SingletonComponent::class)  // Component는 주입된 객체가 유지되는 범위, Singleton, View, ViewModel, or custom...
abstract class DataSourceModule {
  @Binds
  abstract fun bindSampleDataSource(
    sampleDataSourceImpl: SampleDataSourceImpl
  ): SampleDataSource
}
```

```Kotlin
// 2) UseCase와 같이 직접적인 클래스의 생성자 주입이 가능한 경우
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
  @Provides
  @Singleton
  fun provideSampleUseCase(repository: SampleRepository) = SampleUseCase(repository)
}
```

</details>

<details>
<summary><h2>EventFlow vs SharedFlow vs StateFlow vs LiveData</h2></summary>

- `EventFlow`: 토스트, 알럿과 같이 단순 일회성 이벤트인 경우 <br>
  단, 각 ViewModel에는 `하나의 EventFlow`만 추가하는 것을 권장합니다. (sealed interface를 통한 분기)
- ~~`SharedFlow`: EventFlow로 대체되어 usecase 존재하지 않음~~
- `StateFlow`: UI를 그리기 위해 데이터 바인딩을 활용하거나 상태값이 저장되어야 하는 경우 (초기값 O)<br>
  단, lifecycle과 관계가 없어야 하는 경우가 아니라면 `LiveData의 사용을 권장`합니다.
- `LiveData`: UI를 그리기 위해 데이터 바인딩을 활용하거나 상태값이 저장되어야 하는 경우 (초기값 X)

<br>LiveData를 제외한 Flow는 직접 라이프사이클을 지정해주어야합니다.

```kotlin
lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
  // TODO : collect flows
}

// Presentation 모듈에 확장 함수로 등록되어 사용 가능 

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
  lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

    }
  }
}
```

</details>

<details>
<summary><h2>ktlint 사용법</h2></summary>

기본적인 **코드 컨벤션의 통일**을 위해 **ktlint**를 적용하였습니다. <br>
https://cheese10yun.github.io/ktlint/

```bash
./gradlew ktlintCheck  // 전체 스타일 검사

./gradlew ktlintFormat // 스타일에 맞지 않는 코드를 자동 변경

./gradlew addKtlintCheckGitPreCommitHook // 코드 컨벤션이 맞지 않을 경우 commit 에러 처리

```
