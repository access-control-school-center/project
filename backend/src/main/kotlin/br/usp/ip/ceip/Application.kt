package br.usp.ip.ceip

import br.usp.ip.ceip.db.conn
import br.usp.ip.ceip.domain.Person
import br.usp.ip.ceip.domain.security.TokenManager
import io.ktor.application.*
import br.usp.ip.ceip.plugins.*

fun main(args: Array<String>) {
    conn()

//    val lastCount = getLastPersonCount()
//    Person.n = lastCount

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

    configureSecurity(tokenManager)
    configureSerialization()
    configureHTTP(frontendUrl = audience)
    configureRouting(tokenManager)
}
