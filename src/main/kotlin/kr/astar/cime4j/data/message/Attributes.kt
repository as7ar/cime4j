package kr.astar.cime4j.data.message

data class MessageAttributes(
    val sendTimeAt: String?,
    val sid: String?,
    val type: String?,
    val extra: Extra?
)

/**
 * @param id 사용자 ID
 * @param ch 사용자 채널
 * @param c ???
 * @param bg 사용자 뱃지 목록
 * @param dsc ???
 * */
data class UserAttributes(
    val id: String,
    val ch: Channel,
    val c: String,
    val bg: List<Badges>,
    val dsc: Int
)

/**
 * @param id 사용자 ID
 * @param na 사용자 닉네임
 * */
data class Channel(
    val id: String,
    val na: String
)

/**
 * @param id 뱃지 ID
 * @param na 뱃지 이름
 * @param de 뱃지 설명
 * @param ig 뱃지 이미지 링크
 * */
data class Badges(
    val id: String,
    val na: String,
    val de: String?,
    val ig: String?
)