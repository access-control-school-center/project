package br.usp.ip.ceip.domain

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
class Employee(
    override val name: String,
    override val documentType: String,
    override val documentValue: String,
    @Serializable(with = LocalDateSerializer::class)
    override val shotDate: LocalDate,
    override val id: CEIPID? = null,
    val credential: Credential,
) : Person()