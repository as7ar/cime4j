package io.github.astar.cime4j.api

import com.google.gson.Gson
import io.github.astar.cime4j.auth.Auth
import io.github.astar.cime4j.auth.AuthType
import io.github.astar.cime4j.data.channel.ChatMode
import io.github.astar.cime4j.data.channel.LiveInfo
import io.github.astar.cime4j.data.mission.Mission
import io.github.astar.cime4j.data.mission.Missions
import io.github.astar.cime4j.utils.CimeEndpoints
import io.github.astar.cime4j.utils.CimeUtils.getRequest
import java.net.URI

class CimeAPI(
    val id:String,
    val auth : Auth? = null
): ICimeAPI {
    private val gson = Gson()

    override fun fetchChatMode(
        id: String,
    ): ChatMode? {
        return runCatching {
            val uri = URI.create("${CimeEndpoints.API_URL}/app/channels/${this.id}/chat-mode")
            val response = uri.getRequest(this.auth)

            gson.fromJson(response, ChatMode::class.java)
        }.getOrNull()
    }

    override fun fetchLiveInfo(
        id: String,
    ): LiveInfo? {
        if (this.auth?.type== AuthType.TOKEN) {
            return null
        }
        return runCatching {
            val uri = URI.create("${CimeEndpoints.API_URL}/app/channels/${this.id}/live/viewer")
            val response = uri.getRequest(this.auth) ?: return null

//            println("res> ${response}")

            gson.fromJson(response, LiveInfo::class.java)
        }.getOrNull()
    }

    override fun fetchActiveMission(
        id: String,
    ): Missions? {
        if (this.auth?.type== AuthType.TOKEN) {
            return null
        }
        return runCatching {
            val uri = URI.create("${CimeEndpoints.API_URL}/app/channels/$id/active-missions")
            val response = uri.getRequest(this.auth)

            gson.fromJson(response, Missions::class.java)
        }.getOrNull()
    }

    override fun fetchMission(
        id: String,
        missionId: Int,
    ): Mission? {
        return runCatching {
            val uri = URI.create("${CimeEndpoints.API_URL}/app/channels/$id/missions/$missionId")
            val response = uri.getRequest(this.auth)

            gson.fromJson(response, Mission::class.java)
        }.getOrNull()
    }
}