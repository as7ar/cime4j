package io.github.astar.cime4j.events

import io.github.astar.cime4j.data.api.CimeDonation

class DonationEvent(
    val donation: CimeDonation
): CimeEvent() {
    override val eventName: String
        get() = "CimeDonationEvent"
}