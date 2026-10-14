import io.github.astar.cime4j.Cime
import io.github.astar.cime4j.CimeBuilder
import io.github.astar.cime4j.auth.type.CimeApplication
import io.github.astar.cime4j.events.ChatEvent
import java.util.*


fun main() {
    val streamerid="jeokgeukjeoki_przvq4"
    val application = CimeApplication(
        "",
        "",
        "https://localhost:8000"
    )

    println("isOnline: ${Cime.isActive(streamerid)}")

    val cime = CimeBuilder().setID(streamerid)
        .addAuth(application)
        .build()

    println(cime.authManager?.redirectURL)
    val scanner = Scanner(System.`in`)
    val code = scanner.next()
    val access = cime.authManager?.accessTokenGenerator(code=code)
    println(access?.accessToken)

    cime.on<ChatEvent> {
        println("${it.chat.profile.name}:: ${it.chat.content}")
    }

    println("viewer: ${cime.fetchLiveInfo()?.viewerCount}")
}