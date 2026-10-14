package io.github.astar.cime4j.websocket

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.data.api.CimeEventData
import io.github.astar.cime4j.events.CimeEventHandler
import io.github.astar.cime4j.events.ConnectionEvent
import io.github.astar.cime4j.events.DisconnectEvent
import io.github.astar.cime4j.utils.CimeEndpoints
import io.github.astar.cime4j.utils.CimeUtils
import io.github.astar.cime4j.utils.CimeUtils.generateToken
import io.github.astar.cime4j.utils.CimeUtils.getRequest
import io.github.astar.cime4j.utils.CimeUtils.postRequest
import okhttp3.*
import java.net.URI
import java.net.http.HttpRequest
import java.util.*
import java.util.concurrent.TimeUnit

class CimeWebsocket : WebSocketListener {
    private val cime: Cime

    constructor(cime: Cime) : super() {
        this.cime = cime
        this.gson = Gson()
        this.client = OkHttpClient().newBuilder()
            .pingInterval(12, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
        connect()
    }

    private val auths
        get() = cime.getAuth()

    private var client: OkHttpClient
    private lateinit var socket: WebSocket

    private val gson: Gson

    private var isOpenAPI = false
    private val sessionURI= URI.create("${CimeEndpoints.API_URL}/open/v1/sessions/auth/client")
    private var sessionKey = ""

    private fun connect() {
        val openAuth = this.cime.application
        isOpenAPI = openAuth != null

        CimeUtils.execute(
            isOpenAPI,
            {
                val request = Request.Builder()
                    .url(CimeEndpoints.WEBSOCKET_URL)
                    .header("Sec-WebSocket-Protocol",
                        this.cime.generateToken(this.cime.getID())/*.also {
                    println("Token Generated: ${it}")
                }*/ ?: error("유효하지 않은 스트리머 아이디")
                    ).build()
                this.socket = this.client.newWebSocket(request, this)
            },
            {
                listOf("chat", "donation", "subscription").forEach { event ->
                    val uri = URI.create(CimeEndpoints.SUBSCRIBE+event)
                    uri.postRequest()
                }

                val uri = URI.create("${CimeEndpoints.API_URL}/open/v1/sessions/auth/client")
                HttpRequest.newBuilder()
                    .uri(uri).GET()
                    .setHeader("Accept", "application/json, text/plain, */*",)
                    .setHeader("Client-Id", openAuth?.clientId)
                    .setHeader("Client-Secret", openAuth?.clientSecret)
                val res = uri.getRequest() ?: return@execute

                val json = gson.fromJson(res, JsonObject::class.java)
                val url = json["content"].asJsonObject["url"].asString

                val sessionKeyParam = url.substringAfter("sessionKey=")
                sessionKey = sessionKeyParam

                val request = Request.Builder().url(url).build()

                socket = client.newWebSocket(request, this)

                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        this@CimeWebsocket.socket.send("{\"type\":\"PING\"}")
                    }
                }, 0, 60_000)
            }
        )
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        cime.emit(ConnectionEvent(this.cime.getID()))
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        println(text)
        if (text=="{\"action\":\"PONG\"}") return

        try {
            if (isOpenAPI) onMessageOpenAPI(webSocket, text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onMessageOpenAPI(webSocket: WebSocket, text: String) {
        val message = gson.fromJson(text, CimeEventData::class.java)
        val channelId = Cime.fetchChannel(cime.getID())?.id ?: 0
        val handler = CimeEventHandler(cime, channelId)

        when(message.event) {
            "CHAT" -> handler.onChat(text)
            "DONATION" -> handler.onDonation(text)
            "SUBSCRIPTION" -> handler.onSub(text)
            else -> return
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//        println("Closed: ${code}, caused: $reason")
        cime.emit(DisconnectEvent(code, reason))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }
}