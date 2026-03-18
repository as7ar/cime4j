package io.github.astar.cime4j.data.message

import com.google.gson.annotations.SerializedName

data class MessageAttributes(
    val sendTimeAt: String?,
    val sid: String?,
    val type: String?,
    val extra: Extra?
)

/**
 * @param id 사용자 ID
 * @param channel 사용자 채널
 * @param c ???
 * @param badges 사용자 뱃지 목록
 * @param description 사용자 소개말
 * */
data class UserAttributes(
    val id: String,
    @SerializedName("ch") val channel: Channel,
    val c: String,
    @SerializedName("bg") val badges: List<Badge>,
    @SerializedName("dsc") val description: Int
)

/**
 * @param id 사용자 ID
 * @param name 사용자 닉네임
 * */
data class Channel(
    val id: String,
    @SerializedName("na") val name: String
)