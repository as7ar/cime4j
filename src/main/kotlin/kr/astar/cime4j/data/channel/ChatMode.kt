package kr.astar.cime4j.data.channel

data class ChatMode(
    val emojiMode: Boolean,
    val chatAllowedGroup: String,
    val chatDelaySec: Int,
    val minFollowerMinute: Int,
    val followerSubscriberChatAllow: Boolean?,
)
