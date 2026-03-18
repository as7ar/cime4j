package kr.astar.cime4j

import com.google.gson.Gson
import kr.astar.cime4j.cime.CimeChannel
import kr.astar.cime4j.data.channel.ChatMode
import kr.astar.cime4j.data.channel.LiveInfo
import kr.astar.cime4j.data.mission.Mission
import kr.astar.cime4j.data.mission.Missions
import kr.astar.cime4j.event.CimeEvent
import kr.astar.cime4j.utils.CimeUtils
import kr.astar.cime4j.utils.CimeUtils.getRequest
import kr.astar.cime4j.websocket.CimeWebsocket
import java.net.URI

class Cime(private val builder: CimeBuilder) {
    private val gson = Gson()

    companion object {
        @JvmStatic
        fun isActive(id: String) = fetchChannel(id)?.isLive ?: false

        @JvmStatic
        fun fetchChannel(id: String): CimeChannel? {
            return runCatching {
                val uri= CimeUtils.fetchJsonLive(id)?.asJsonObject["bodyData"]?.asJsonObject ?: return null
                val channel=uri["live"]?.asJsonObject["channel"]?.asJsonObject ?: return null

                Gson().fromJson(channel, CimeChannel::class.java)
            }.getOrNull()
        }
    }

    private var  id: String = this.builder.id ?: error("")
    private var debug: Boolean = this.builder.debug
    init {
        CimeWebsocket(this)
    }

    fun getID() = this.id

    fun getAuth() = this.builder.auth

    @PublishedApi
    internal val handlerMap: MutableMap<Class<out CimeEvent>, MutableList<(CimeEvent) -> Unit>> = mutableMapOf()

    inline fun <reified T : CimeEvent> on(noinline action: (T) -> Unit) {
        val clazz = T::class.java
        val list = handlerMap.getOrPut(clazz) { mutableListOf() }

        list.add { event ->
            action(event as T)
        }
    }

    internal inline fun <reified T : CimeEvent> emit(obj: T) {
        val clazz = T::class.java
        handlerMap[clazz]?.forEach { it(obj) }
    }

    fun fetchChatMode(): ChatMode? {
        return runCatching {
            val uri = URI.create("https://ci.me/api/app/channels/${this.id}/chat-mode")
            val response = uri.getRequest(getAuth())

            gson.fromJson(response, ChatMode::class.java)
        }.getOrNull()
    }

    fun fetchLiveInfo(): LiveInfo? {
        return runCatching {
            val uri = URI.create("https://ci.me/api/app/channels/${this.id}/live/viewer?isWatchingUhd=false")
            val response = uri.getRequest(getAuth()) ?: return null

//            println(response)

            gson.fromJson(response, LiveInfo::class.java)
        }.getOrNull()
    }

    fun fetchActiveMission(): Missions? {
        return runCatching {
            val uri = URI.create("https://ci.me/api/app/channels/$id/active-missions")
            val response = uri.getRequest(getAuth())

            gson.fromJson(response, Missions::class.java)
        }.getOrNull()
    }

    fun fetchMission(missionId: Int): Mission? {
        return runCatching {
            val uri = URI.create("https://ci.me/api/app/channels/$id/missions/$missionId")
            val response = uri.getRequest(getAuth())

            gson.fromJson(response, Mission::class.java)
        }.getOrNull()
    }
}