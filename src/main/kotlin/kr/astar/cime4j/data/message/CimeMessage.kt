package kr.astar.cime4j.data.message

import java.util.*

data class CimeMessage(
    val type: String, // EVENT, MESSAGE
    val id: String,
    val requestId: UUID,
    val eventName: String?, // DONATION_CHAT, DONATION_MISSION_REWARD_ADDED

    val attributes: MessageAttributes,

    val content: String?,
    val sendTime: String,

    val sender: User?
)
