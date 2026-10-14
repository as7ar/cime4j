package io.github.astar.cime4j.events

import io.github.astar.cime4j.data.api.CimeSubscription

class SubscriptionEvent(
    val subscription: CimeSubscription
): CimeEvent() {
    override val eventName: String
        get() = "CimeSubscriptionEvent"
}