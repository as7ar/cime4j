package kr.astar.cime4j.data.message

data class MessageAttributes(
    val sendTimeAt: String?,
    val sid: String?,
    val type: String?,
    val extra: Extra?
)

data class UserAttributes(
    val user: String
)