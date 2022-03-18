package br.usp.ip.ceip.plugins

import io.ktor.http.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.server.engine.*

fun Application.configureHTTP(frontendUrl: String) {
    install(CORS) {
        allowCredentials = true

        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        header(HttpHeaders.ContentType)
        host(frontendUrl)
    }
}
