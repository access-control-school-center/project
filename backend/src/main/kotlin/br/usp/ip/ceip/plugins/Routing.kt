package br.usp.ip.ceip.plugins

import br.usp.ip.ceip.api.AuthController
import br.usp.ip.ceip.api.LoginPayload
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting(authController: AuthController) {

    routing {
        post("/login") {
            val payload = call.receive<LoginPayload>()
            val (httpStatus, message) = authController.login(payload)
            call.respond(httpStatus, message)
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
