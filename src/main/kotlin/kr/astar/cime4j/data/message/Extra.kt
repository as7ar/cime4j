package kr.astar.cime4j.data.message

import com.google.gson.annotations.SerializedName

data class Extra(
    val dId: String,
    @SerializedName("msg") val message: String,
    val ttsId: String,
    @SerializedName("amt") val amount: Int,
    @SerializedName("anon") val isAnon: Boolean,
    val prof: UserAttributes,
    val aUrl: String? = null
)