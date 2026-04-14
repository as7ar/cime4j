package io.github.astar.cime4j.data.api

/**
 * @description 씨미 OpenAPI 응답 형식
 *
 * @property code 응답 코드
 * @property message 응답 메세지
 * @property content 응답 내용 [com.google.gson.JsonObject]
 * */
data class Response(
    val code: Int,
    val message: String?,
    val content: Any?
)
