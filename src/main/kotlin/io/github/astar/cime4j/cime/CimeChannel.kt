package io.github.astar.cime4j.cime

class CimeChannel(
    val id: Int, // 채널 ID
    val slug: String, // 채널 핸들(슬러그)

    val name: String, // 채널 이름
    val description: String, // 채널 설명

    val followerCount: Int, // 팔로워 수
    val subscriberCount: Int, // 구독자 수

    val isLive: Boolean, // 현재 라이브 여부
    val level: Int
) {
    override fun toString(): String {
        return ""
    }
}