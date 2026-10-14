package io.github.astar.cime4j.data.legacy.message

import com.google.gson.annotations.SerializedName

@Deprecated(message = "Use Official API")
data class Extra(
    val dId: String,
    @SerializedName("msg") val message: String,
    val ttsId: String,
    @SerializedName("amt") val amount: Int,
    @SerializedName("anon") val isAnon: Boolean,
    val prof: UserAttributes,
    val aUrl: String? = null
)

/**
 * @param id 뱃지 ID
 * @param name 뱃지 이름
 * @param description 뱃지 설명
 * @param image 뱃지 이미지 링크
 * */
@Deprecated(message = "Use Official API")
data class Badge(
    val id: String,
    @SerializedName("na") val name: String,
    @SerializedName("de") val description: String?,
    @SerializedName("ig") val image: String?
)