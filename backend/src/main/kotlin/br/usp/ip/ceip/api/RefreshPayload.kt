package br.usp.ip.ceip.api

import kotlinx.serialization.Serializable

@Serializable
data class RefreshPayload(
    val token: String
)
