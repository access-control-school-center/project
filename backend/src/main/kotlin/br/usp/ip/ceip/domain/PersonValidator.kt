package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException
import br.usp.ip.ceip.domain.exceptions.ValidationException

class PersonValidator(
    private val personRepository: PersonRepository
) {
    private fun validateDocumentUniqueness(type: String, value: String) {
        when (type) {
            "RG" -> {
                try {
                    personRepository.findOneByRG(value)

                    throw ValidationException("Person", "document [RG]", "already in use")
                } catch (e: PersonNotFoundException) {
                    return
                }
            }

            "CPF" -> {
                try {
                    personRepository.findOneByCPF(value)

                    throw ValidationException("Person", "document [CPF]", "already in use")
                } catch (e: PersonNotFoundException) {
                    return
                }
            }

            else -> throw ValidationException("Person", "document [CPF, RG]", "must have either RG or CPF")
        }
    }

    fun validateCreation(person: Person) {
        validateDocumentUniqueness(person.documentType, person.documentValue)
    }
}