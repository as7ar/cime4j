package io.github.astar.cime4j.events

import com.google.gson.Gson
import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.data.api.CimeChat
import io.github.astar.cime4j.data.api.CimeDonation
import io.github.astar.cime4j.data.api.CimeSubscription

internal class CimeEventHandler(
    private val cime: Cime,
    private val channelId: Int,
) {
    private val gson = Gson()

    fun onChat(text: String) {
        val response = gson.fromJson(text, CimeChat::class.java)
        if (response.channelId.toIntOrNull() != channelId) return
        cime.emit(ChatEvent(response))
    }

    fun onDonation(text: String) {
        val response = gson.fromJson(text, CimeDonation::class.java)
        if (response.channelId.toIntOrNull() != channelId) return
        cime.emit(DonationEvent(response))
    }

    fun onSub(text: String) {
        val response = gson.fromJson(text, CimeSubscription::class.java)
        if (response.channelId.toIntOrNull() != channelId) return
        cime.emit(SubscriptionEvent(response))
    }
}
