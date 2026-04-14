package io.github.astar.cime4j.data.channel

/**
 * @property title 방송 제목
 * @property clipActive 클립 생성 가능 여부
 * @property isAdult 성인 콘텐츠 여부
 * @property canSubscription 구독 가능 여부
 * @property canChatDonation 채팅 후원 가능 여부
 * @property canMissionDonation 미션 후원 가능 여부
 * @property canVideoDonation 영상 후원 가능 여부
 * @property showSponsorRank 후원 랭킹 표시 여부
 * @property viewerCount 현재 시청자 수
 * @property serverDate 서버 기준 시간
 * @property canWatchUhd UHD 시청 가능 여부
 * @property urlUhd UHD 스트림 URL
 */
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