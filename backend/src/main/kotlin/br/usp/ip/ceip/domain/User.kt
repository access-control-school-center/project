package br.usp.ip.ceip.domain

import java.time.LocalDate

import kotlinx.serialization.Serializable

@Serializable
class User(
    override val name: String,
    override val documentType: String,
    override val documentValue: String,
    @Serializable(with = LocalDateSerializer::class)
    override val shotDate: LocalDate,
    override val id: CEIPID? = null,
    val services: Set<String> = emptySet<String>()
): Person()
