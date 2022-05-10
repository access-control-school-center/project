package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.CEIPID
import br.usp.ip.ceip.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

data class UserUpdatePayload(
    val name: String,
    val documentType: String,
    val documentValue: String,
    @Serializable(with = LocalDateSerializer::class)
    val shotDate: LocalDate,
    val services: Set<String>
)