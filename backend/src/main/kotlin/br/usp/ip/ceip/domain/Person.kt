package br.usp.ip.ceip.domain

import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val documentType: String,
    val documentValue: String,
    @Serializable(with = LocalDateSerializer::class)
    val shotDate: LocalDate,
)