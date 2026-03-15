package kr.astar.cime4j.event

import kr.astar.cime4j.data.message.User

class ChatEvent(
    val channel: String,
    val content: String,
    val sender: User
): CimeEvent() {
    override val eventName: String
        get() = "CimeChatEvent"
}