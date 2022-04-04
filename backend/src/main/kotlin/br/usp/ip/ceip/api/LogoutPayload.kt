package br.usp.ip.ceip.api

import kotlinx.serialization.Serializable

@Serializable
data class LogoutPayload(
    val token: String
)
