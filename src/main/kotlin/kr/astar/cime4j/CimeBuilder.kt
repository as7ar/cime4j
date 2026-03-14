package kr.astar.cime4j

class CimeBuilder {
    internal var id: String? = null
    internal var debug: Boolean = false

    fun setID(streamerID: String) = apply { this.id=streamerID }

    fun setDebug(enable: Boolean) = apply { this.debug = enable }

    fun build(): Cime {
        return Cime(this)
    }
}