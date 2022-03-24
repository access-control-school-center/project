package br.usp.ip.ceip.api

import io.ktor.http.*

data class ControllerResult(
    val httpStatus: HttpStatusCode,
    val message: Map<String, String>
)
