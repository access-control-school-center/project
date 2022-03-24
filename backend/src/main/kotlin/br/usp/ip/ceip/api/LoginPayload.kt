package br.usp.ip.ceip.api

@kotlinx.serialization.Serializable
data class LoginPayload(
    val nusp: String,
    val password: String
)