package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.CredentialNotFoundException
import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException
import br.usp.ip.ceip.domain.exceptions.ValidationException

open class PersonValidator(
    private val personRepository: PersonRepository,
    private val documentValidator: DocumentValidator,
    private val shotDateValidator: ShotDateValidator,
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

            "Não Documentado" -> {
                return
            }

            else -> throw ValidationException("Person", "document [CPF, RG]", "must have either RG or CPF")
        }
    }

    fun validateCreation(person: Person) {
        validateDocumentUniqueness(person.documentType, person.documentValue)
        documentValidator.validateDocument(person)
        shotDateValidator.validateShotDate(person)
    }

    open fun validateUpdate(previous: Person, updated: Person) {
        val changedType = previous.documentType != updated.documentType
        val changedValue = previous.documentValue != updated.documentValue
        val changedDocument = changedType || changedValue

        if (changedDocument) documentValidator.validateDocument(updated)

        if (previous.shotDate != updated.shotDate) shotDateValidator.validateShotDate(updated)
    }
}