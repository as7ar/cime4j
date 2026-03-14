package kr.astar.cime4j.websocket

import com.google.gson.Gson
import com.google.gson.JsonObject
import kr.astar.cime4j.Cime
import kr.astar.cime4j.data.CimeMessage
import kr.astar.cime4j.data.Extra
import kr.astar.cime4j.data.User
import kr.astar.cime4j.data.attributes.MessageAttributes
import kr.astar.cime4j.data.attributes.UserAttributes
import kr.astar.cime4j.utils.CimeUtils.generateToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.UUID
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
                generateToken(this.cime.getID()) ?: error("유효하지 않은 스트리머 아이디")
            ).build()
        this.socket = this.client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {

    }


    override fun onMessage(webSocket: WebSocket, text: String) {
        val json = gson.fromJson(text, JsonObject::class.java)

        val type=json["Type"].asString
        val id= json["Id"].asString
        val requestID= UUID.fromString(json["RequestId"].asString)

        val eventName= json["EventName"].asString

        val msgAttributes=json["Attributes"].asJsonObject
        val sendTimeAt=msgAttributes["sendTimeAt"].asString
        val sid = msgAttributes["sid"].asString
        val messageType = msgAttributes["type"].asString

        val extraJson = msgAttributes["extra"].asJsonObject

        val extra = gson.fromJson(extraJson, Extra::class.java)

        val messageAttributes= MessageAttributes(
            sendTimeAt, sid, messageType, extra
        )

        val content=json["Content"].asString
        val sendTime = json["SendTime"].asString

        val sender=json["Sender"].asJsonObject
        val userID= sender["UserId"].asInt
        val usrAttributes = sender["Attributes"].asJsonObject["user"].asString
        val user = User(
            userID, UserAttributes(usrAttributes)
        )

        val cimeMessage = CimeMessage(
            type, id, requestID, eventName, messageAttributes,
            content, sendTime, user
        )
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        println("Closed: ${code}, caused: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }
}