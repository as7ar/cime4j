package io.github.astar.cime4j.events

import io.github.astar.cime4j.data.api.CimeChat

class ChatEvent(
    val chat: CimeChat
): CimeEvent() {
    override val eventName: String
        get() = "CimeChatEvent"
}