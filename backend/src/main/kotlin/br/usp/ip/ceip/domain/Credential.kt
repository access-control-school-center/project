package br.usp.ip.ceip.domain

@kotlinx.serialization.Serializable
class Credential(
    val nusp: String,
    val passwordHash: String,
)