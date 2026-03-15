package kr.astar.cime4j.websocket

import com.google.gson.Gson
import kr.astar.cime4j.Cime
import kr.astar.cime4j.utils.CimeUtils.generateMessage
import kr.astar.cime4j.utils.CimeUtils.generateToken
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
                generateToken(this.cime.getID())/*.also {
                    println("Token Generated: ${it}")
                }*/ ?: error("유효하지 않은 스트리머 아이디")
            ).build()
        this.socket = this.client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        println(text)
        try {
            val cimeMessage = generateMessage(text) ?: return

            println(cimeMessage)
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