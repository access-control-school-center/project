package br.usp.ip.ceip.api

import br.usp.ip.ceip.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EmployeeCreationPayload(
    val name: String,
    val documentType: String,
    val documentValue: String,
    @Serializable(with = LocalDateSerializer::class)
    val shotDate: LocalDate,
    val credential: CredentialCreationPayload
)
