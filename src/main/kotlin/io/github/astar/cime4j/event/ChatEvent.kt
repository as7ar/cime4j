package io.github.astar.cime4j.event

import io.github.astar.cime4j.data.message.User

class ChatEvent(
    val channel: String,
    val content: String,
    val userName: String,
    val sender: User
): CimeEvent() {
    override val eventName: String
        get() = "CimeChatEvent"
}