package kr.astar.cime4j.data.message

data class Extra(
    val dId: String,
    val msg: String,
    val ttsId: String,
    val amt: Int,
    val anon: Boolean,
    val prof: Prof,
    val aUrl: String? = null
)

data class Prof(
    val id: String,
    val ch: Ch,
    val c: String,
    val bg: List<Badge>,
    val dsc: Int
)

data class Ch(
    val id: String,
    val na: String
)

data class Badge(
    val id: String,
    val na: String,
    val de: String,
    val ig: String
)