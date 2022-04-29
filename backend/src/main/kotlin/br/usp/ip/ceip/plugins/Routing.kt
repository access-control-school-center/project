package br.usp.ip.ceip.plugins

import br.usp.ip.ceip.api.*
import br.usp.ip.ceip.domain.User
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

        post("/logout") {
            val payload = call.receive<LogoutPayload>()
            val (httpStatus, message) = authController.logout(payload)
            call.respond(httpStatus, message)
        }

        authenticate {
            post("/users") {
                val user = call.receive<User>()
                val (httpStatus, message) = personController.register(user)
                call.respond(httpStatus, message)
            }

            post("/employees") {
                val employee = call.receive<EmployeeCreationPayload>()
                val (httpStatus, message) = personController.register(employee)
                call.respond(httpStatus, message)
            }

            get("/users") {
                val params = call.request.queryParameters
                val (httpStatus, message) = personController.searchUsers(params)
                call.respond(httpStatus, message)
            }

            get("/employees") {
                val params = call.request.queryParameters
                val (httpStatus, message) = personController.searchEmployees(params)
                call.respond(httpStatus, message)
            }

            put("/Users/{id}") {
                val id = call.parameters["id"]
                val updatedEmployee = call.receive<EmployeeUpdatePayload>()
                val (httpStatus, message) = personController.updateEmployee(id, updatedEmployee)
                call.respond(httpStatus, message)
            }
        }
    }
}
