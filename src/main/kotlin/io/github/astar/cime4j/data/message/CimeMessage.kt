package io.github.astar.cime4j.data.message

import io.github.astar.cime4j.enums.EventName
import java.util.*

data class CimeMessage(
    val type: String, // EVENT, MESSAGE
    val id: String,
    val requestId: UUID,
    val eventName: EventName?, // DONATION_CHAT, DONATION_MISSION_REWARD_ADDED, MIDROLL_START, DONATION_MISSION_UPDATED

    val attributes: MessageAttributes,

    val content: String?,
    val sendTime: String,

    val sender: User?
)
