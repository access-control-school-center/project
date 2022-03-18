package br.usp.ip.ceip.plugins

import br.usp.ip.ceip.domain.security.TokenManager
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting(manager: TokenManager) {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/login") {
            val accessToken = manager.generateAccessToken("anyone")
            val refreshToken = manager.generateRefreshToken("anyone")

            return@post call.respond(
                status = HttpStatusCode.OK,
                message = mapOf(
                    "access_token" to accessToken,
                    "refresh_token" to refreshToken
                )
            )
        }

        authenticate {
            get("/foo") {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = mapOf("msg" to "I see you're logged in")
                )
            }
        }
    }
}
