import kr.astar.cime4j.CimeBuilder
import kr.astar.cime4j.auth.CimeCookie

fun main() {
    val cime= CimeBuilder().setID("irocloud")
        .setAuth(CimeCookie(
            $$""
        ))
        .build()

    println("viewer: ${cime.fetchLiveInfo()?.viewerCount}")
}