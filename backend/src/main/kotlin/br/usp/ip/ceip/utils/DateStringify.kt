package br.usp.ip.ceip.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

fun dateToString(date: LocalDate): String {
    return formatter.format(date)
}

fun dateStringToLocalDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString, formatter)
}