package io.github.astar.cime4j.data.api.profile

import com.google.gson.JsonArray

data class CimeProfile(
    var name: String,
    var badges: JsonArray,
)