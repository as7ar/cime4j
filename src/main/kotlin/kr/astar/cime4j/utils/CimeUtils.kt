package kr.astar.cime4j.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object CimeUtils {
    fun generateToken(streamerID: String): String? {
        val url = URI.create("https://ci.me/api/app/channels/${streamerID}/chat-token")
        val client = HttpClient.newHttpClient()
        val request= HttpRequest.newBuilder()
            .uri(url).POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Accept", "application/json, text/plain, */*").build()

        try {
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() == 200) {
                val body = response?.body() ?: return null
                val json = Gson().fromJson(body, JsonObject::class.java)
                val token = json["data"].asJsonObject["token"].asString
                return token
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}