package br.usp.ip.ceip.domain

import at.favre.lib.crypto.bcrypt.BCrypt
import br.usp.ip.ceip.domain.security.TokenManager

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)

fun login(
    nusp: String,
    password: String,
    credentialRepository: CredentialRepository,
    manager: TokenManager): TokenPair {

    val credential = credentialRepository.findOneByNusp(nusp)
    val result = BCrypt
        .verifyer()
        .verify(password.toCharArray(), credential.passwordHash)

    if (result.verified) {
        val accessToken = manager.generateAccessToken(nusp)
        val refreshToken = manager.generateRefreshToken(nusp)

        return TokenPair(accessToken, refreshToken)
    } else {
        throw Exception("invalid nusp or password")
    }
}

fun refreshLogin(
    refreshToken: String,
    manager: TokenManager,
): TokenPair {
    val decodedJWT = manager.validateRefresh(refreshToken)
    val nusp = decodedJWT.getClaim("nusp").asString()
    val accessToken = manager.generateAccessToken(nusp)
    return TokenPair(accessToken, refreshToken)
}