package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.*
import br.usp.ip.ceip.domain.exceptions.ValidationException
import io.ktor.http.*

class PersonController(
    private val personRepository: PersonRepository,
    private val personValidator: PersonValidator
) {
    fun register(person: Person): ControllerResult<Any> {
        val role: String = if (person is User) "user" else "employee"

        try {
            val registered = register(
                person,
                personRepository,
                personValidator
            )

            return ControllerResult (
                httpStatus = HttpStatusCode.OK,
                message = mapOf(role to registered)
            )
        } catch (e: ValidationException) {
            return ControllerResult (
                httpStatus = HttpStatusCode.BadRequest,
                message = mapOf("error" to e.message!!)
            )
        }
    }

    fun search(params: Parameters): ControllerResult<Any> {
        val sanitizedParams = mutableMapOf<Params, String?>()

        sanitizedParams[Params.DOC_TYPE] = params[Params.DOC_TYPE.key]
        sanitizedParams[Params.DOC_VALUE] = params[Params.DOC_VALUE.key]
        sanitizedParams[Params.NAME] = params[Params.NAME.key]
        sanitizedParams[Params.CEIPID] = params[Params.CEIPID.key]

        val people = searchPeople(sanitizedParams, personRepository)

        return ControllerResult(
            httpStatus = HttpStatusCode.OK,
            message = mapOf("people" to people)
        )
    }
}