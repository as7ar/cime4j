package kr.astar.cime4j.event

import kr.astar.cime4j.data.message.User

class DonationEvent(
    val sender: User,
    val amount: Long
): CimeEvent() {
    override val eventName: String
        get() = "CimeDonationEvent"
}