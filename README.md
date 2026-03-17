# 씨미 비공식 API
![](/images/cime4j_banner.png)

[![](https://jitpack.io/v/as7ar/cime4j.svg)](https://jitpack.io/#as7ar/cime4j)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/as7ar/cime4j)

## 🗃️ import the library

```groovy
repositories {
    maven { url = uri("https://jitpack.io") }
}



dependencies {
    implementation("com.github.as7ar:cime4j:Tag")
}
```

## 📄Usage

```kotlin
import kr.astar.cime4j.Cime
import kr.astar.cime4j.CimeBuilder
import kr.astar.cime4j.auth.CimeCookie
import kr.astar.cime4j.event.ChatEvent

val cime = CimeBuilder()  
    .setID("streamer_id")
    .setAuth(CimeCookie("cookie_value")) // 스트리머 정보 조회시 필요 
    .build()

cime.on<ChatEvent> { it->
    println("${it.userName}: ${it.content}")
}
```