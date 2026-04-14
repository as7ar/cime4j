package io.github.astar.cime4j.data.mission

import com.google.gson.annotations.SerializedName

/**
 * @param id 미션 아이디
 * @param isAnon 익명 유저 여부
 * @param state 미션 완료 여부
 * @param reward 미션 완료 보상
 * @param description 미션 내용
 * @param timeOut 미션 완료까지 남은 시간
 *
 * @param supporters 미션 기여자(들)
 * */
data class Mission(
    val id: Int,
    @SerializedName("isAnonymous") val isAnon: Boolean,
    val state: String,
    val reward: Int,
    val description: String,
    val timeOut: Int,

    val supporters: List<Supporter>

)

/**
 * @param id 사용자 아이디
 * @param name 사용자 닉네임
 * @param amount 후원
 * */
data class Supporter(
    val id: String,
    val name: String,
    val amount: Int
) {}