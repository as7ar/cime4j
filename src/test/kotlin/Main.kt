import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.CimeBuilder
import io.github.astar.cime4j.auth.CimeCookie
import io.github.astar.cime4j.event.ChatEvent


fun main() {
    val streamerid=""
    val cookie= $$""
    println("isOnline ${Cime.isActive(streamerid)}")

    val cime = CimeBuilder().setID(streamerid)
        .addAuth(CimeCookie(cookie))
        .build()

    cime.on<ChatEvent> {
        println("${it.sender.attributes.channel.name}:: ${it.content}")
    }

    println("viewer: ${cime.fetchLiveInfo()?.viewerCount}")
}