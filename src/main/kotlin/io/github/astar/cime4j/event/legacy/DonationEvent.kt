package io.github.astar.cime4j.event.legacy

import io.github.astar.cime4j.data.legacy.message.User
import io.github.astar.cime4j.event.CimeEvent

class DonationEvent(
    val sender: User,
    val amount: Long
): CimeEvent() {
    override val eventName: String
        get() = "CimeDonationEvent"
}