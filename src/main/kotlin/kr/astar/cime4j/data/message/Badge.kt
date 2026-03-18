package kr.astar.cime4j.data.message

import com.google.gson.annotations.SerializedName

/**
 * @param id 뱃지 ID
 * @param name 뱃지 이름
 * @param description 뱃지 설명
 * @param image 뱃지 이미지 링크
 * */
data class Badge(
    val id: String,
    @SerializedName("na") val name: String,
    @SerializedName("de") val description: String?,
    @SerializedName("ig") val image: String?
)