package io.github.astar.cime4j.event.legacy

import io.github.astar.cime4j.data.legacy.message.User
import io.github.astar.cime4j.event.CimeEvent

class ChatEvent(
    val channel: String,
    val content: String,
    val userName: String,
    val sender: User
): CimeEvent() {
    override val eventName: String
        get() = "CimeChatEvent"
}