package br.usp.ip.ceip.api

import kotlinx.serialization.Serializable

@Serializable
data class CredentialCreationPayload(
    val nusp: String,
    val password: String
)
