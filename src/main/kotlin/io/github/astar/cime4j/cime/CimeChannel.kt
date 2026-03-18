package io.github.astar.cime4j.cime

class CimeChannel(
    val id: Int,
    val slug: String,

    val name: String,
    val description: String,

    val followerCount: Int,
    val subscriberCount: Int,

    val isLive: Boolean,
    val level: Int
) {
    override fun toString(): String {
        return ""
    }
}