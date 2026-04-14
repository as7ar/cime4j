package io.github.astar.cime4j.auth

class CimeToken(
    private val accessToken: String,
): Auth() {
    override val type: AuthType = AuthType.TOKEN

    override fun getAuth(): String {
        return this.accessToken
    }
}