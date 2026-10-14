package io.github.astar.cime4j.data.legacy.message

import com.google.gson.annotations.SerializedName

@Deprecated(message = "Use Official API")
data class User(
    @SerializedName("userID") val id: Int,
    val attributes: UserAttributes
)
