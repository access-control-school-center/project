package br.usp.ip.ceip.domain

import java.time.LocalDate

data class Person(
    val name: String,
    val documentType: String,
    val documentValue: String,
    val shotDate: LocalDate,
)