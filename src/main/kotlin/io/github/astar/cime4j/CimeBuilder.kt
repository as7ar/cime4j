package io.github.astar.cime4j

import io.github.astar.cime4j.auth.Auth

class CimeBuilder {
    internal var id: String? = null
    internal var debug: Boolean = false
    internal var authList: MutableList<Auth> = mutableListOf()

    /**
    * @param streamerID 스트리머 아이디 설정
    * */
    fun setID(streamerID: String) = apply { this.id=streamerID }

    /**
     * @param auth 인증 방식 추가 [io.github.astar.cime4j.auth.CimeCookie] 또는 [io.github.astar.cime4j.auth.CimeApplication] 사용
     * */
    fun addAuth(vararg auth: Auth) = apply {
        auth.forEach { this.authList.add(it) }
    }

    fun setDebug(enable: Boolean) = apply { this.debug = enable }

    /**
     * @return [Cime]
     *
     * */
    fun build(): Cime {
        require(id != null) { "streamerID is required" }
        return Cime(this)
    }
}