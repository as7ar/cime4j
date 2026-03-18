package kr.astar.cime4j.data.mission

import com.google.gson.annotations.SerializedName

data class Mission(
    val id: Int,
    @SerializedName("isAnonymous") val isAnon: Boolean,
    val state: String,
    val reward: Int,
    val description: String,
    val timeOut: Int,

    val supporters: List<Supporter>

)

data class Supporter(
    val id: String,
    val name: String,
    val amount: Int
) {}