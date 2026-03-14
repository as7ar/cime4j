package kr.astar.cime4j

import kr.astar.cime4j.websocket.CimeWebsocket


class Cime(private val builder: CimeBuilder) {
    private var  id: String = this.builder.id ?: error("")
    private var debug: Boolean = this.builder.debug

    init {
        CimeWebsocket(this)
    }

    fun getID() = this.id
}