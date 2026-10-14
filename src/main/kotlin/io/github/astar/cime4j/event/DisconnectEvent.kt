package io.github.astar.cime4j.event

class DisconnectEvent(
    val code: Int,
    val reason: String,
): CimeEvent() {
    override val eventName: String
        get() = "DisconnectEvent"
}