package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.Person
import io.ktor.http.*

class PersonController(
    private val personRepository: PersonRepository
) {
    fun register(person: Person): ControllerResult<Any> {
        val errorResponse = mapOf("error" to "person already registered")

        try {
            val registeredPerson = br.usp.ip.ceip.domain.register(
                person,
                personRepository
            )

            return ControllerResult (
                httpStatus = HttpStatusCode.OK,
                message = mapOf("person" to registeredPerson)
            )
        } catch (e: Exception) {
            return ControllerResult (
                httpStatus = HttpStatusCode.BadRequest,
                message = errorResponse
            )
        }
    }
}