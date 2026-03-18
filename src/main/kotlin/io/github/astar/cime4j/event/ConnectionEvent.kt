package io.github.astar.cime4j.event

class ConnectionEvent(
    val channelId: String
): CimeEvent() {
    override val eventName: String
        get() = "CimeConnectionEvent"
}