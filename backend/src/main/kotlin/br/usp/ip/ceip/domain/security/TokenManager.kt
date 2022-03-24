package br.usp.ip.ceip.domain.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.Date

class TokenManager(
    private val issuer: String,
    private val audience: String,
    val realm: String,
    accessTokenSecret: String,
    refreshTokenSecret: String,
) {

    private val accessAlgorithm: Algorithm
    private val refreshAlgorithm: Algorithm
    private val activeRefreshTokens: MutableSet<String>

    val accessVerifier: JWTVerifier
        get() = JWT
            .require(accessAlgorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    private val refreshVerifier: JWTVerifier
        get() = JWT
            .require(refreshAlgorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    init {
        accessAlgorithm = Algorithm.HMAC256(accessTokenSecret)
        refreshAlgorithm = Algorithm.HMAC256(refreshTokenSecret)
        activeRefreshTokens = mutableSetOf()
    }

    fun validateAccess(credential: JWTCredential): Principal? {
        return if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
    }

    fun generateAccessToken(nusp: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("nusp", nusp)
            .withExpiresAt(fifteenMinutesFromNow())
            .sign(accessAlgorithm)
    }

    fun generateRefreshToken(nusp: String): String {
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("nusp", nusp)
            .withExpiresAt(aDayFromNow())
            .sign(refreshAlgorithm)

        activeRefreshTokens.add(token)

        return token
    }

    fun validateRefresh(refreshToken: String): DecodedJWT {
        if (refreshToken !in activeRefreshTokens)
            throw JWTVerificationException("Refresh token not found")

        return refreshVerifier.verify(refreshToken)
    }

    fun cancelRefreshToken(token: String) {
        activeRefreshTokens.remove(token)
    }

    private fun fifteenMinutesFromNow(): Date {
        val fifteenMinutesMillis = 15 * 60 * 1000
        return futureDate(fifteenMinutesMillis)
    }

    private fun aDayFromNow(): Date {
        val aDayMillis = 24 * 60 * 60 * 1000
        return futureDate(aDayMillis)
    }

    private fun futureDate(delta: Int): Date {
        return Date(System.currentTimeMillis() + delta)
    }
}