package kr.astar.cime4j.auth

data class CimeCookie(
    val cookie: String
): Auth() {
    override val type: String
        get() = "cookie"
    override fun getAuth(): String {
        return this.cookie
    }
}