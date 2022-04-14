package br.usp.ip.ceip.domain

import java.time.LocalDate

sealed class Person() {
    abstract val name: String
    abstract val documentType: String
    abstract val documentValue: String
    abstract val shotDate: LocalDate
    abstract val id: CEIPID?
}