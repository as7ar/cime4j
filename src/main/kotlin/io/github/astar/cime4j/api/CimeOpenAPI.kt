package io.github.astar.cime4j.api

import io.github.astar.cime4j.auth.CimeApplication
import io.github.astar.cime4j.data.channel.ChatMode
import io.github.astar.cime4j.data.channel.LiveInfo
import io.github.astar.cime4j.data.mission.Mission
import io.github.astar.cime4j.data.mission.Missions

class CimeOpenAPI(
    val channelIDs: List<String>,
    val client: CimeApplication
): ICimeAPI {
    override fun fetchChatMode(id: String): ChatMode? {
        TODO("Not yet implemented")
    }

    override fun fetchLiveInfo(id: String): LiveInfo? {
        TODO("Not yet implemented")
    }

    override fun fetchActiveMission(id: String): Missions? {
        TODO("Not yet implemented")
    }

    override fun fetchMission(id: String, missionId: Int): Mission? {
        TODO("Not yet implemented")
    }

}