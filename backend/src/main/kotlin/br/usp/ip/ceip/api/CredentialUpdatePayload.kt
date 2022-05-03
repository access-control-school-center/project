package br.usp.ip.ceip.api

import kotlinx.serialization.Serializable

@Serializable
class CredentialUpdatePayload (
    val nusp: String,
    val password: String = ""
)

