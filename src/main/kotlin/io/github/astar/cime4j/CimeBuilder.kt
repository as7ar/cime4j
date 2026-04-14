package io.github.astar.cime4j

import io.github.astar.cime4j.auth.Auth

class CimeBuilder {
    internal var id: String? = null
    internal var debug: Boolean = false
    internal var authList: MutableList<Auth> = mutableListOf()

    fun setID(streamerID: String) = apply { this.id=streamerID }

    fun addAuth(vararg auth: Auth) = apply {
        auth.forEach { this.authList.add(it) }
    }

    fun setDebug(enable: Boolean) = apply { this.debug = enable }

    fun build(): Cime {
        require(id != null) { "streamerID is required" }
        return Cime(this)
    }
}