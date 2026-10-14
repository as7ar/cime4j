package io.github.astar.cime4j.data.api

import com.google.gson.JsonArray
import io.github.astar.cime4j.data.api.profile.CimeProfile

data class CimeEventData(
    var event: String,
    var data: String,
)

data class CimeChat(
    var channelId: String,
    var senderChannelId: String,

    var profile: CimeProfile,
    var content: String,

    var messageTime: String,
    var emojis: Map<String, String>
)

data class CimeDonation(
    var donationType: String,

    var channelId: String,

    var donatorChannelId: String,
    var donatorNickname: String,
    var donatorBadges: JsonArray,

    var payAmount: String,
    var payAmountNumber: Int,

    var donateText: String,
    var emojis: Map<String, String>,
    var cheeringItems: JsonArray,
)

data class CimeSubscription(
    var channelId: String,

    var subscriberChannelId: String,
    var subscriberChannelName: String,
    var subscriberBadges: JsonArray,

    var month: Int,
    var tierNo: Int,
    var tierName: String,
    var subscriptionText: String,
    var emojis: Map<String, String>
)