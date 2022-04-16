package br.usp.ip.ceip.domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Credential(
    val nusp: String,
    @Transient
    val passwordHash: String = ""
)