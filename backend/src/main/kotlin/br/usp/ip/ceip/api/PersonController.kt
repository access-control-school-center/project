package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.*
import br.usp.ip.ceip.domain.exceptions.ValidationException
import io.ktor.http.*

class PersonController(
    private val personRepository: PersonRepository,
    private val personValidator: PersonValidator
) {
    fun register(user: User): ControllerResult<Any> {
        return try {
            val registered = register(
                user,
                personRepository,
                personValidator
            )

            ControllerResult (
                httpStatus = HttpStatusCode.OK,
                message = mapOf("user" to registered)
            )
        } catch (e: ValidationException) {
            ControllerResult (
                httpStatus = HttpStatusCode.BadRequest,
                message = mapOf("error" to e.message!!)
            )
        }
    }

    fun register(employeeCreationPayload: EmployeeCreationPayload): ControllerResult<Any> {
        val (name, documentType, documentValue, shotDate, credential) = employeeCreationPayload

        return try {
            val employee = register(
                name,
                documentType,
                documentValue,
                shotDate,
                nusp = credential.nusp,
                password = credential.password,
                personRepository,
                personValidator
            )

            ControllerResult(
                httpStatus = HttpStatusCode.OK,
                message = mapOf("employee" to employee)
            )
        } catch (e: ValidationException) {
            ControllerResult(
                httpStatus = HttpStatusCode.BadRequest,
                message = mapOf("error" to e.message!!)
            )
        }
    }

    fun searchUsers(params: Parameters): ControllerResult<Any> {
        val sanitizedParams = sanitizeParams(params)

        val users = searchUsers(
            sanitizedParams,
            personRepository
        )

        return ControllerResult(
            httpStatus = HttpStatusCode.OK,
            message = mapOf("users" to users)
        )
    }

    fun searchEmployees(params: Parameters): ControllerResult<Any> {
        val sanitizedParams = sanitizeParams(params)

        val employees = searchEmployees(
            sanitizedParams,
            personRepository
        )

        return ControllerResult(
            httpStatus = HttpStatusCode.OK,
            message = mapOf("employees" to employees)
        )
    }

    private fun sanitizeParams(params: Parameters): Map<Params, String?> {
        val sanitizedParams = mutableMapOf<Params, String?>()

        sanitizedParams[Params.DOC_TYPE] = params[Params.DOC_TYPE.key]
        sanitizedParams[Params.DOC_VALUE] = params[Params.DOC_VALUE.key]
        sanitizedParams[Params.NAME] = params[Params.NAME.key]
        sanitizedParams[Params.CEIPID] = params[Params.CEIPID.key]

        return sanitizedParams
    }
}