package io.github.astar.cime4j.data.channel

/**
 * @property emojiMode 이모지 전용 채팅 모드 여부
 * @property chatAllowedGroup 채팅이 허용된 사용자 그룹 (ALL, FOLLOWER, SUBSCRIBER)
 * @property chatDelaySec 채팅 메시지 간 최소 지연 시간
 * @property minFollowerMinute 채팅 참여를 위해 요구되는 최소 팔로우 시간
 * @property followerSubscriberChatAllow 팔로워/구독자 채팅 허용 여부
 * */
data class ChatMode(
    val emojiMode: Boolean,
    val chatAllowedGroup: String,
    val chatDelaySec: Int,
    val minFollowerMinute: Int,
    val followerSubscriberChatAllow: Boolean?,
)
