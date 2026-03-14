package kr.astar.cime4j.data.attributes

import kr.astar.cime4j.data.Extra

data class MessageAttributes(
    val sendTimeAt: String?,
    val sid: String?,
    val type: String?,
    val extra: Extra?
)
