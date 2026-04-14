package io.github.astar.cime4j.websocket

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.enums.EventName
import io.github.astar.cime4j.event.ChatEvent
import io.github.astar.cime4j.event.ConnectionEvent
import io.github.astar.cime4j.utils.CimeEndpoints
import io.github.astar.cime4j.utils.CimeUtils
import io.github.astar.cime4j.utils.CimeUtils.generateMessage
import io.github.astar.cime4j.utils.CimeUtils.generateToken
import io.github.astar.cime4j.utils.CimeUtils.getRequest
import okhttp3.*
import java.net.URI
import java.net.http.HttpRequest
import java.util.Timer
import java.util.TimerTask
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
            if (isOpenAPI) {
                onMessageOpenAPI(webSocket, text)
            } else onMessageLegacy(text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onMessageLegacy(text: String) {
        val cimeMessage = generateMessage(text) ?: return

        if (cimeMessage.type =="MESSAGE") {
            cime.emit(ChatEvent(
                cime.getID(),
                cimeMessage.content ?: return,
                cimeMessage.sender?.attributes?.channel?.name ?: return,
                cimeMessage.sender
            ))
        }

        if (cimeMessage.type=="EVENT") {
            //TODO: Donation Event
            when(cimeMessage.eventName) {
                EventName.DONATION_CHAT -> {}
                EventName.DONATION_MISSION_REWARD_ADDED -> {}
                EventName.MIDROLL_START -> {}
                EventName.DONATION_MISSION_UPDATED -> {}
                EventName.SUBSCRIPTION_MESSAGE -> {}
                null, EventName.EMPTY -> {}
            }
        }
    }

    fun onMessageOpenAPI(webSocket: WebSocket, text: String) {

    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        println("Closed: ${code}, caused: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }
}