# 씨미 비공식 Java JDK
![](/images/cime4j_banner.png)

[![](https://jitpack.io/v/as7ar/cime4j.svg)](https://jitpack.io/#as7ar/cime4j)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
![GitHub License](https://img.shields.io/github/license/as7ar/cime4j?style=flat-square)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/as7ar/cime4j)

## 🗃️ Import the library

```gradle
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

val isActive = Cime.isActive("streamer_id")
val channel = Cime.fetchChannel("streamer_id")

cime.on<ChatEvent> { it->
    println("${it.userName}: ${it.content}")
}
```

# 🔨 Tech Stack

| Component | Link                                          | Content             | Status |
|-----------|-----------------------------------------------|---------------------|--------|
| chzzk4j   | [Link](https://github.com/R2turnTrue/chzzk4j) | Event Listener      | ✅      |

# 👤Support Discord Server
https://discord.gg/BCqWwvr888
