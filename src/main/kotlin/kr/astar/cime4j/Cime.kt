package kr.astar.cime4j

import com.google.gson.Gson
import kr.astar.cime4j.data.channel.LiveInfo
import kr.astar.cime4j.utils.CimeUtils.getRequest
import kr.astar.cime4j.websocket.CimeWebsocket
import java.net.URI


class Cime(private val builder: CimeBuilder) {
    private var  id: String = this.builder.id ?: error("")
    private var debug: Boolean = this.builder.debug

    init {
        CimeWebsocket(this)
    }

    fun getID() = this.id

    fun fetchChatMode() {
        val uri = URI.create("https://ci.me/api/app/channels/${this.id}/chat-mode")
        val response = uri.getRequest()
    }

    fun fetchLiveInfo(): LiveInfo? {
        val uri = URI.create("https://ci.me/api/app/channels/${this.id}/live/viewer?isWatchingUhd=false")
        val response = uri.getRequest() ?: return null
        return Gson().fromJson(response, LiveInfo::class.java)
    }

    fun fetchMission() {
        val uri= URI.create("https://ci.me/api/app/channels/${this.id}/active-missions")
        val response = uri.getRequest()
    }
}