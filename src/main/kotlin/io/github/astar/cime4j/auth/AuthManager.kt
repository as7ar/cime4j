package io.github.astar.cime4j.auth

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.astar.cime4j.auth.type.CimeApplication
import io.github.astar.cime4j.auth.type.CimeToken
import io.github.astar.cime4j.data.api.Response
import io.github.astar.cime4j.utils.CimeEndpoints
import io.github.astar.cime4j.utils.CimeUtils.postRequest
import java.net.URI

class AuthManager(
    val auth: CimeApplication,
) {
    private val gson = Gson()
    private val authUrl = URI.create("${CimeEndpoints.API_URL}/auth/v1/token")
    private val revokeUrl = URI.create("${CimeEndpoints.API_URL}/auth/v1/revoke")

    val redirectURL = "https://ci.me/auth/openapi/account-interlock?clientId=${this.auth.clientId}&redirectUri=${this.auth.redirectURL}&state=state"

    var token: CimeToken? = null
    var scope: List<Scope> = mutableListOf()

    private val tokenExpiry: Long = 3600 // 1h

    fun accessTokenGenerator(
        grantType: GrantType=GrantType.AUTHORIZATION_CODE,
        code: String?=null,
        refreshToken: String?=null
    ): CimeToken? {
        val params= JsonObject().apply {
            addProperty("grantType", grantType.name.lowercase())
            addProperty("clientId", this@AuthManager.auth.clientId)
            addProperty("clientSecret", this@AuthManager.auth.clientSecret)

            when(grantType) {
                GrantType.AUTHORIZATION_CODE-> {
                    require(code!=null)
                    addProperty("code", code ?: "")
                }
                GrantType.REFRESH_TOKEN-> {
                    require(refreshToken!=null)
                    addProperty("refreshToken", refreshToken ?: "")
                }
            }
        }

        val req= gson.fromJson(
            authUrl.postRequest(params.asString),
            Response::class.java
        )
        val data = gson.fromJson(
            req.content.toString(),
            JsonObject::class.java
        )

        this.token = CimeToken(
            data["accessToken"].asString,
            data["refreshToken"].asString
        )
        this.scope = data["scope"].asString.split(" ").map { Scope.valueOf(it) }

        return this.token
    }

    fun revokeToken(
        token:String,
        grantType: GrantType
    ): Boolean {
        val params= JsonObject().apply {
            addProperty("clientId", this@AuthManager.auth.clientId)
            addProperty("clientSecret", this@AuthManager.auth.clientSecret)

            addProperty("token", token)
            addProperty("tokenTypeHint", grantType.name.lowercase())
        }

        val req= revokeUrl.postRequest(params.asString)

        return req!=null
    }

    fun refresh(): CimeToken? = accessTokenGenerator(
        GrantType.REFRESH_TOKEN,
        refreshToken = this.token?.refreshToken
    )

    enum class GrantType {
        AUTHORIZATION_CODE,
        REFRESH_TOKEN,
    }

    enum class Scope(val str: String) {
        READ_CHANNEL("READ:CHANNEL"),
        READ_LIVE_STREAM_KEY("READ:LIVE_STREAM_KEY"),
        READ_LIVE_STREAM_SETTINGS("READ:LIVE_STREAM_SETTINGS"),
        WRITE_LIVE_STREAM_SETTINGS("WRITE:LIVE_STREAM_SETTINGS"),
        READ_USER("READ:USER"),
        READ_LIVE_CHAT("READ:LIVE_CHAT"),
        WRITE_LIVE_CHAT("WRITE:LIVE_CHAT"),
        WRITE_LIVE_CHAT_NOTICE("WRITE:LIVE_CHAT_NOTICE"),
        READ_LIVE_CHAT_SETTINGS("READ:LIVE_CHAT_SETTINGS"),
        WRITE_LIVE_CHAT_SETTINGS("WRITE:LIVE_CHAT_SETTINGS"),
        WRITE_USER_BLOCK("WRITE:USER_BLOCK"),
        READ_USER_BLOCK("READ:USER_BLOCK"),
        READ_DONATION("READ:DONATION"),
        READ_SUBSCRIPTION("READ:SUBSCRIPTION"),
    }
}

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
)