package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.CredentialRepository
import br.usp.ip.ceip.domain.security.TokenManager
import io.ktor.http.*

class AuthController(
    val manager: TokenManager,
    val credentialRepository: CredentialRepository
) {
    fun login(payload: LoginPayload): ControllerResult {
        val errorResponse = mapOf("error" to "invalid nusp or password")

        val (nusp, password) = payload

        try {
            val (accessToken, refreshToken) = br.usp.ip.ceip.domain.login(
                nusp,
                password,
                credentialRepository,
                manager
            )

            return ControllerResult(
                httpStatus = HttpStatusCode.OK,
                message = mapOf(
                    "access_token" to accessToken,
                    "refresh_token" to refreshToken
                )
            )
        } catch (e: Exception) {
            return ControllerResult(
                httpStatus = HttpStatusCode.BadRequest,
                message = errorResponse
            )
        }
    }
}