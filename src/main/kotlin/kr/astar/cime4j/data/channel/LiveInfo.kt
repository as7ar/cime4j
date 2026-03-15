package kr.astar.cime4j.data.channel

data class LiveInfo(
    val title: String,

    val clipActive: Boolean,
    val isAdult: Boolean,
    val canSubscription: Boolean,
    val canChatDonation: Boolean,
    val canMissionDonation: Boolean,
    val canVideoDonation: Boolean,
    val showSponsorRank: Boolean,

    val viewerCount: Int,

    val serverDate: String,
    val canWatchUhd: Boolean,

    val urlUhd: String
)