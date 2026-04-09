package io.github.astar.cime4j.auth

import com.google.gson.JsonObject

class CimeToken(
    val clientId: String,
    val accessToken: String
): Auth() {
    override val type: AuthType
        get() = AuthType.TOKEN

    override fun getAuth(): String {
        return JsonObject().apply {
            addProperty("clientid", this@CimeToken.clientId)
            addProperty("accesstoken", this@CimeToken.accessToken)
        }.asString
    }
}