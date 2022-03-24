package br.usp.ip.ceip

import br.usp.ip.ceip.api.AuthController
import br.usp.ip.ceip.db.CredentialRepositoryImpl
import br.usp.ip.ceip.domain.security.TokenManager
import br.usp.ip.ceip.plugins.configureHTTP
import br.usp.ip.ceip.plugins.configureRouting
import br.usp.ip.ceip.plugins.configureSecurity
import br.usp.ip.ceip.plugins.configureSerialization
import io.ktor.application.*

fun main(args: Array<String>) {
//    conn()

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val issuer = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()
    val accessSecret = environment.config.property("jwt.access_secret").getString()
    val refreshSecret = environment.config.property("jwt.refresh_secret").getString()

    val tokenManager = TokenManager(issuer, audience, realm, accessSecret, refreshSecret)

    val repo = CredentialRepositoryImpl()

    val authController = AuthController(tokenManager, repo)

    configureSecurity(tokenManager)
    configureSerialization()
    configureHTTP(frontendUrl = audience)
    configureRouting(authController)
}
