package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.Person
import br.usp.ip.ceip.domain.PersonValidator
import br.usp.ip.ceip.domain.exceptions.ValidationException
import io.ktor.http.*

class PersonController(
    private val personRepository: PersonRepository,
    private val personValidator: PersonValidator
) {
    fun register(person: Person): ControllerResult<Any> {
        try {
            val registeredPerson = br.usp.ip.ceip.domain.register(
                person,
                personRepository,
                personValidator
            )

            return ControllerResult (
                httpStatus = HttpStatusCode.OK,
                message = mapOf("person" to registeredPerson)
            )
        } catch (e: ValidationException) {
            return ControllerResult (
                httpStatus = HttpStatusCode.BadRequest,
                message = mapOf("error" to e.message!!)
            )
        }
    }
}