import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.CimeBuilder
import io.github.astar.cime4j.auth.CimeCookie
import io.github.astar.cime4j.event.ChatEvent


fun main() {
    println("isOnline ${Cime.isActive("irocloud")}")

    val cime= CimeBuilder().setID("irocloud")
        .setAuth(
            CimeCookie(
                $$""
            )
        )
        .build()

    cime.on<ChatEvent> {
    //        println("${it.sender.attributes.ch.na}:: ${it.content}")
    }

//    println("viewer: ${cime.fetchLiveInfo()?.viewerCount}")
}