package br.usp.ip.ceip.plugins

import br.usp.ip.ceip.api.AuthController
import br.usp.ip.ceip.api.LoginPayload
import br.usp.ip.ceip.api.PersonController
import br.usp.ip.ceip.api.RefreshPayload
import br.usp.ip.ceip.domain.Person
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting(authController: AuthController, personController: PersonController) {

    routing {
        post("/login") {
            val payload = call.receive<LoginPayload>()
            val (httpStatus, message) = authController.login(payload)
            call.respond(httpStatus, message)
        }

        post("/token") {
            val payload = call.receive<RefreshPayload>()
            val (httpStatus, message) = authController.refresh(payload)
            call.respond(httpStatus, message)
        }

        authenticate {
            post("/register") {
                val person = call.receive<Person>()
                val (httpStatus, message) = personController.register(person)
                call.respond(httpStatus, message)
            }

            get("/people") {
                val params = call.request.queryParameters
                val (httpStatus, message) = personController.search(params)
                call.respond(httpStatus, message)
            }
        }
    }
}
