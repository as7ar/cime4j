package io.github.astar.cime4j.websocket

import com.google.gson.Gson
import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.enums.EventName
import io.github.astar.cime4j.event.ChatEvent
import io.github.astar.cime4j.event.ConnectionEvent
import io.github.astar.cime4j.utils.CimeUtils.generateMessage
import io.github.astar.cime4j.utils.CimeUtils.generateToken
import okhttp3.*
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

    private var client: OkHttpClient
    private lateinit var socket: WebSocket

    private val gson: Gson

    private fun connect() {
        val request = Request.Builder()
            .url("wss://edge.ivschat.ap-northeast-2.amazonaws.com/")
            .header("Sec-WebSocket-Protocol",
                this.cime.generateToken(this.cime.getID())/*.also {
                    println("Token Generated: ${it}")
                }*/ ?: error("유효하지 않은 스트리머 아이디")
            ).build()
        this.socket = this.client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        cime.emit(ConnectionEvent(this.cime.getID()))
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        println(text)
        try {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        println("Closed: ${code}, caused: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }
}