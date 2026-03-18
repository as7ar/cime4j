package io.github.astar.cime4j.data.message

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userID") val id: Int,
    val attributes: UserAttributes
)
