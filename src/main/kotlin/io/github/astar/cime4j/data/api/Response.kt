package io.github.astar.cime4j.data.api

data class Response(
    val code: Int,
    val message: String?,
    val content: Any?
)
