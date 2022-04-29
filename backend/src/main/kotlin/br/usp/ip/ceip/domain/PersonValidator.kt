package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.CredentialNotFoundException
import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException
import br.usp.ip.ceip.domain.exceptions.ValidationException

class PersonValidator(
    private val personRepository: PersonRepository,
    private val documentValidator: DocumentValidator,
    private val shotDateValidator: ShotDateValidator,
    private val credentialRepository: CredentialRepository
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

    fun validateCredential(nusp: String, password: String) {
        validateNuspUniqueness(nusp)
        validatePasswordStructure(password)
    }

    fun validateUpdate(previous: Employee, updated: Employee) {

        if (previous.documentValue != updated.documentValue ||
            previous.documentType !=  updated.documentType) {
            documentValidator.validateDocument(updated)
        }

        if (previous.shotDate != updated.shotDate) shotDateValidator.validateShotDate(updated)

        if (previous.credential != null) {
            validateCredential(previous.credential.nusp, previous.credential.passwordHash)
        }
    }

    private fun validatePasswordStructure(password: String) {
        val pwdRegex: Regex = "^[a-z0-9!@#$%&-_]{8,20}$".toRegex(RegexOption.IGNORE_CASE)
        if (!pwdRegex.matches(password)) throw ValidationException("Credential", "password", "malformed password")
    }

    private fun validateNuspUniqueness(nusp: String) {
        try {
            credentialRepository.findOneByNusp(nusp)
            throw ValidationException("Credential", "nusp", "already in use")
        } catch (e: CredentialNotFoundException) {
            return
        }
    }
}