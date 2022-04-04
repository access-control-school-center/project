package br.usp.ip.ceip.api

import io.ktor.http.*

data class ControllerResult<T>(
    val httpStatus: HttpStatusCode,
    val message: Map<String, T>
)
