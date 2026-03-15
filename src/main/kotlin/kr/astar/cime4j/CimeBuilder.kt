package kr.astar.cime4j

import kr.astar.cime4j.auth.Auth

class CimeBuilder {
    internal var id: String? = null
    internal var debug: Boolean = false
    internal var auth: Auth? = null

    fun setID(streamerID: String) = apply { this.id=streamerID }

    fun setAuth(auth: Auth) = apply { this.auth = auth }

    fun setDebug(enable: Boolean) = apply { this.debug = enable }

    fun build(): Cime {
        return Cime(this)
    }
}