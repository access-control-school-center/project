package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.CredentialRepository
import br.usp.ip.ceip.domain.exceptions.TokenNotFoundException
import br.usp.ip.ceip.domain.refreshLogin
import br.usp.ip.ceip.domain.security.TokenManager
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.http.*

class AuthController(
    private val manager: TokenManager,
    private val credentialRepository: CredentialRepository
) {
    fun login(payload: LoginPayload): ControllerResult<Any> {
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

    fun refresh(payload: RefreshPayload): ControllerResult<Any> {
        return try {
            val (token) = payload
            val (accessToken, refreshToken) = refreshLogin(token, manager)
            ControllerResult(
                HttpStatusCode.OK,
                message = mapOf(
                    "access_token" to accessToken,
                    "refresh_token" to refreshToken
                )
            )
        } catch (e: JWTVerificationException) {
            ControllerResult(
                httpStatus = HttpStatusCode.Unauthorized,
                message = mapOf("error" to e.message!!)
            )
        }
    }

    fun logout(payload: LogoutPayload): ControllerResult<Any> {
        val (token) = payload
        return try {
            manager.cancelRefreshToken(token)
            ControllerResult(
                httpStatus = HttpStatusCode.OK,
                message = mapOf("message" to "logged out")
            )
        } catch (e: TokenNotFoundException) {
            ControllerResult(
                httpStatus = HttpStatusCode.BadRequest,
                message = mapOf("error" to e.message!!)
            )
        }
    }
}