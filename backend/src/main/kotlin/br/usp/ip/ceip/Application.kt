package br.usp.ip.ceip

import br.usp.ip.ceip.api.AuthController
import br.usp.ip.ceip.api.PersonController
import br.usp.ip.ceip.db.CredentialRepositoryImpl
import br.usp.ip.ceip.db.PersonRepositoryImpl
import br.usp.ip.ceip.db.RefreshTokenRepositoryImpl
import br.usp.ip.ceip.db.setupDatabase
import br.usp.ip.ceip.domain.DocumentValidator
import br.usp.ip.ceip.domain.Person
import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.PersonValidator
import br.usp.ip.ceip.domain.ShotDateValidator
import br.usp.ip.ceip.domain.security.RefreshTokenRepository
import br.usp.ip.ceip.domain.security.TokenManager
import br.usp.ip.ceip.plugins.configureHTTP
import br.usp.ip.ceip.plugins.configureRouting
import br.usp.ip.ceip.plugins.configureSecurity
import br.usp.ip.ceip.plugins.configureSerialization
import br.usp.ip.ceip.utils.dateToString
import io.ktor.application.*
import java.time.LocalDate
import java.time.ZoneId

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    setupDatabase(
        url = environment.config.property("database.url").getString(),
        driver = environment.config.property("database.driver").getString(),
        user = environment.config.property("database.user").getString(),
        password = environment.config.property("database.password").getString(),
    )

    val tokenRepo: RefreshTokenRepository = RefreshTokenRepositoryImpl()

    val issuer = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()
    val accessSecret = environment.config.property("jwt.access_secret").getString()
    val refreshSecret = environment.config.property("jwt.refresh_secret").getString()

    val tokenManager = TokenManager(
        tokenRepo,
        issuer,
        audience,
        realm,
        accessSecret,
        refreshSecret
    )

    val credentialRepository = CredentialRepositoryImpl()
    val personRepository = PersonRepositoryImpl()

    val documentValidator = DocumentValidator()
    val shotDateValidator = ShotDateValidator()
    val personValidator = PersonValidator(personRepository, documentValidator, shotDateValidator)

    val authController = AuthController(tokenManager, credentialRepository)
    val personController = PersonController(personRepository, personValidator)

    configureSecurity(tokenManager)
    configureSerialization()
    configureHTTP(frontendUrl = audience)
    configureRouting(authController, personController)

}
