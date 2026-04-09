package io.github.astar.cime4j.auth

data class CimeCookie(
    val cookie: String
): Auth() {
    override val type: AuthType
        get() = AuthType.COOKIE
    override fun getAuth(): String {
        return this.cookie
    }
}