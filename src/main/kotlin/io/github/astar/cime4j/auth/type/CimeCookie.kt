package io.github.astar.cime4j.auth.type

import io.github.astar.cime4j.auth.Auth
import io.github.astar.cime4j.auth.AuthType

@Deprecated(message = "Use Official API")
data class CimeCookie(
    val cookie: String
): Auth() {
    override val type: AuthType
        get() = AuthType.COOKIE
    override fun getAuth(): String {
        return this.cookie
    }
}