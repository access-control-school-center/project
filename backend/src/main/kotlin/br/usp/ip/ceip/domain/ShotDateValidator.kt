package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.ValidationException
import java.time.LocalDate
import java.time.ZoneId

class ShotDateValidator {
    private fun validateDate(date: LocalDate) {
        val z : ZoneId = ZoneId.of("Brazil/East")
        val today : LocalDate = LocalDate.now(z)

        if (date.isAfter(today)) throw ValidationException("Person", "shotDate", "invalid date")
    }

    fun validateShotDate(person: Person) {
        validateDate(person.shotDate)
    }
}