package br.usp.ip.ceip.domain.exceptions

class PersonNotFoundException(
    documentType: String,
    documentValue: String
) : Exception("Person [$documentType=$documentValue] not found") {
}