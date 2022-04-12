package br.usp.ip.ceip.domain

import java.time.LocalDate
import java.time.ZoneId

class ShotDateValidator {
    private fun validateDate(date: LocalDate) : Boolean {
        val z : ZoneId = ZoneId.of("Brazil/East")
        val today : LocalDate = LocalDate.now(z)

        return !date.isAfter(today)
    }

    fun validateShotDate(shotDate: LocalDate) : Boolean {
        return validateDate(shotDate)
    }
}