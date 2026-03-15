package kr.astar.cime4j.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import kr.astar.cime4j.Cime
import kr.astar.cime4j.auth.Auth
import kr.astar.cime4j.data.message.*
import kr.astar.cime4j.enums.EventName
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

object CimeUtils {
    fun Cime.generateToken(streamerID: String): String? {
        val url = URI.create("https://ci.me/api/app/channels/${streamerID}/chat-token")
        val json = url.postRequest(this.getAuth()) ?: return null
        val token = json["token"].asString
        return token
    }

    fun URI.postRequest(auth: Auth?): JsonObject? {
        val request= HttpRequest.newBuilder()
            .uri(this).POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Accept", "application/json, text/plain, */*").build()

        return request(request)
    }

    fun URI.getRequest(auth: Auth?): JsonObject? {
        val request= HttpRequest.newBuilder()
            .uri(this).GET()
            .setHeader("Accept", "application/json, text/plain, */*")

        if (auth?.type=="cookie") request.setHeader("Cookie", auth.getAuth())

        return request(request.build())
    }

    private fun request(request: HttpRequest): JsonObject? {

        val client = HttpClient.newHttpClient()

        try {
            val response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
            )

            if (response.statusCode()==400) {
                println(response?.body())
            }

            if (response.statusCode() == 200) {
                val body = response?.body() ?: return null

                println(body)

                val json = Gson().fromJson(body, JsonObject::class.java)
                val data = json.get("data")?.asJsonObject ?: json

                return data
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private val gson: Gson = Gson()
    fun generateMessage(text: String): CimeMessage? {
        try {
            val json = gson.fromJson(text, JsonObject::class.java)

            val type = json.get("Type")?.asString ?: return null
            val id = json.get("Id")?.asString ?: return null

            val requestID = json.get("RequestId")?.asString?.let {
                UUID.fromString(it)
            } ?: return null

            val eventName = json.get("EventName")?.asString

            val attributes = json.getAsJsonObject("Attributes") ?: return null

            val sendTimeAt = attributes.get("sendTimeAt")?.asString
            val sid = attributes.get("sid")?.asString
            val messageType = attributes.get("type")?.asString ?: return null

            var extra: Extra? = null
            attributes.get("extra")?.let {
                try {
                    val extraStr = it.asString
                    extra = gson.fromJson(extraStr, Extra::class.java)
                } catch (_: Exception) {}
            }

            val messageAttributes = MessageAttributes(
                sendTimeAt,
                sid,
                messageType,
                extra
            )

            val content = json.get("Content")?.asString
            val sendTime = json.get("SendTime")?.asString ?: ""

            var user: User? = null

            json.getAsJsonObject("Sender")?.let { sender ->

                val userId = sender.get("UserId")?.asString?.toIntOrNull() ?: return@let

                val userJsonString = sender
                    .getAsJsonObject("Attributes")
                    ?.get("user")
                    ?.asString ?: return@let

                val userAttributes = runCatching {
                    gson.fromJson(userJsonString, UserAttributes::class.java)
                }.getOrNull() ?: return@let

                user = User(userId, userAttributes)
            }

            return CimeMessage(
                type,
                id,
                requestID,
                EventName.valueOf(eventName ?: "EMPTY"),
                messageAttributes,
                content,
                sendTime,
                user
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    internal fun fetchJsonLive(id: String): JsonObject? {
        val uri = URI.create("https://ci.me/json/@${id}/live")
        val request= HttpRequest.newBuilder()
            .uri(uri).GET()
            .setHeader("Accept", "application/json, text/plain, */*")
            .build()
        return request(request)
    }
}