package io.github.astar.cime4j

import com.google.gson.Gson
import io.github.astar.cime4j.api.CimeAPI
import io.github.astar.cime4j.auth.AuthType
import io.github.astar.cime4j.cime.CimeChannel
import io.github.astar.cime4j.data.channel.ChatMode
import io.github.astar.cime4j.data.channel.LiveInfo
import io.github.astar.cime4j.data.mission.Mission
import io.github.astar.cime4j.data.mission.Missions
import io.github.astar.cime4j.event.CimeEvent
import io.github.astar.cime4j.utils.CimeUtils
import io.github.astar.cime4j.websocket.CimeWebsocket

class Cime(private val builder: CimeBuilder) {
    private val gson = Gson()

    companion object {
        /**
         * @param id 스트리머 아이디
         *
         * @return 라이브 여부 [Boolean]
         * */
        @JvmStatic
        fun isActive(id: String) = fetchChannel(id)?.isLive ?: false

        /**
         * @param id 스트리머 아이디
         *
         * @return [CimeChannel]
         * */
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

    /**
     * @return 스트리머 아이디
     * */
    fun getID() = this.id

    /**
     * @return 인증 방식 [List]
     * */
    fun getAuth() = this.builder.authList.toList()

    @PublishedApi
    internal val handlerMap: MutableMap<Class<out CimeEvent>, MutableList<(CimeEvent) -> Unit>> = mutableMapOf()

    /**
     * @param action 이벤트 클래스 [CimeEvent]
     * */
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

    private val api = CimeAPI(this.id, getAuth().find { it.type== AuthType.COOKIE })

    fun fetchChatMode(): ChatMode? {
        return api.fetchChatMode(this.id)
    }

    fun fetchLiveInfo(): LiveInfo? {
        return api.fetchLiveInfo(this.id)
    }

    fun fetchActiveMission(): Missions? {
        return api.fetchActiveMission(this.id)
    }

    fun fetchMission(missionId: Int): Mission? {
        return api.fetchMission(this.id, missionId)
    }
}