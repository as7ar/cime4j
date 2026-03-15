package kr.astar.cime4j

import com.google.gson.Gson
import kr.astar.cime4j.cime.CimeChannel
import kr.astar.cime4j.data.channel.LiveInfo
import kr.astar.cime4j.event.CimeEvent
import kr.astar.cime4j.utils.CimeUtils
import kr.astar.cime4j.utils.CimeUtils.getRequest
import kr.astar.cime4j.websocket.CimeWebsocket
import java.net.URI
import java.util.function.Consumer
import kotlin.collections.mutableListOf

class Cime(private val builder: CimeBuilder) {

    companion object {
        @JvmStatic
        fun isActive(id: String) = fetchChannel(id)?.isLive ?: false

        @JvmStatic
        fun fetchChannel(id: String): CimeChannel? {
            val uri= CimeUtils.fetchJsonLive(id) ?: return null
            val channel=uri["live"]?.asJsonObject["channel"]?.asJsonObject ?: return null

            val cimeChannel = CimeChannel(
                channel["id"]?.asInt ?: return null,
                channel["slug"]?.asString ?: return null,
                channel["name"]?.asString ?: return null,
                channel["description"]?.asString ?: return null,
                channel["followerCount"]?.asInt ?: return null,
                channel["subscriberCount"]?.asInt ?: return null,
                channel["isLive"]?.asBoolean ?: return null,
                channel["level"]?.asInt ?: return null
            )

            return cimeChannel
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

    inline fun <reified T : CimeEvent> emit(obj: T) {
        val clazz = T::class.java
        handlerMap[clazz]?.forEach { it(obj) }
    }

    fun fetchChatMode() {
        val uri = URI.create("https://ci.me/api/app/channels/${this.id}/chat-mode")
        val response = uri.getRequest(getAuth())
    }

    fun fetchLiveInfo(): LiveInfo? {
        try {
            val uri = URI.create("https://ci.me/api/app/channels/${this.id}/live/viewer?isWatchingUhd=false")
            val response = uri.getRequest(getAuth()) ?: return null

//            println(response)

            return Gson().fromJson(response, LiveInfo::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun fetchActiveMission() {
        val uri= URI.create("https://ci.me/api/app/channels/${this.id}/active-missions")
        //https://ci.me/api/app/channels/{id}/missions/{mission-id}
        val response = uri.getRequest(getAuth())
    }

    fun fetchMission(missionId: Int) {
        val uri = URI.create("https://ci.me/api/app/channels/${this.id}/missions/${missionId}")
        val response = uri.getRequest(getAuth())
    }
}