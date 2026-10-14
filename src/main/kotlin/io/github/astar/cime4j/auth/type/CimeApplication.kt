package io.github.astar.cime4j.auth.type

import com.google.gson.JsonObject
import io.github.astar.cime4j.auth.Auth
import io.github.astar.cime4j.auth.AuthType

class CimeApplication(
    val clientId: String,
    val clientSecret: String,

    val redirectURL: String
): Auth() {
    override val type: AuthType
        get() = AuthType.APPLICATION

    override fun getAuth(): String {
        return JsonObject().apply {
            addProperty("clientid", this@CimeApplication.clientId)
            addProperty("clientsecret", this@CimeApplication.clientSecret)
        }.asString
    }
}