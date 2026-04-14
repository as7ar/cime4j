package io.github.astar.cime4j.api.auth

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.astar.cime4j.auth.CimeApplication
import io.github.astar.cime4j.data.api.Response
import io.github.astar.cime4j.utils.CimeEndpoints
import io.github.astar.cime4j.utils.CimeUtils
import io.github.astar.cime4j.utils.CimeUtils.postRequest
import java.net.URI
import java.net.http.HttpRequest

class AuthManager(
    val auth: CimeApplication,
) {
    private val gson = Gson()
    private val authUrl = URI.create("${CimeEndpoints.API_URL}/auth/v1/token")
    private val revokeUrl = URI.create("${CimeEndpoints.API_URL}/auth/v1/revoke")

    val redirectURL = "https://ci.me/auth/openapi/account-interlock?clientId=${this.auth.clientId}&redirectUri=${this.auth.redirectURL}&state=state"

    var accessToken: String = ""
    var refreshToken: String = ""
    var scope: String = ""

    private val tokenExpiry: Long = 3600 // 1h

    fun accessTokenGenerator(
        grantType: GrantType=GrantType.AUTHORIZATION_CODE,
        code: String?=null,
        refreshToken: String?=null
    ): String? {
        val params= JsonObject().apply {
            addProperty("grantType", grantType.name.lowercase())
            addProperty("clientId", this@AuthManager.auth.clientId)
            addProperty("clientSecret", this@AuthManager.auth.clientSecret)

            when(grantType) {
                GrantType.AUTHORIZATION_CODE-> {
                    addProperty("code", code ?: "")
                }
                GrantType.REFRESH_TOKEN-> {
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

        this.refreshToken = data["refreshToken"].asString
        this.scope = data["scope"].asString

        return data["accessToken"].asString
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

    enum class GrantType {
        AUTHORIZATION_CODE,
        REFRESH_TOKEN,
    }
}

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
)