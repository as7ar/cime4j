package kr.astar.cime4j.data.mission

data class Missions(
    val missions: List<Mission>,
    val pagination: pagInation
)

data class pagInation(
    val currentPage: Int,
    val pageSize: Int,
    val totalCount: Int
)