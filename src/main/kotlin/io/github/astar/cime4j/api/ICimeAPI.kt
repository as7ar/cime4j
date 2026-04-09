package io.github.astar.cime4j.api

import io.github.astar.cime4j.data.channel.ChatMode
import io.github.astar.cime4j.data.channel.LiveInfo
import io.github.astar.cime4j.data.mission.Mission
import io.github.astar.cime4j.data.mission.Missions

interface ICimeAPI {
    fun fetchChatMode(id: String): ChatMode?
    fun fetchLiveInfo(id: String): LiveInfo?
    fun fetchActiveMission(id: String): Missions?
    fun fetchMission(id: String, missionId: Int): Mission?
}