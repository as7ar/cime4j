package io.github.astar.cime4j.auth

class CimeToken(
    val accessToken: String,
    val refreshToken: String,
): Auth() {
    override val type: AuthType = AuthType.TOKEN

    override fun getAuth(): String {
        return this.accessToken
    }
}