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

    private fun validateServices(services: Set<String>) {
        for (s in services) {
            if (s.isEmpty()) {
                throw ValidationException("User", "services", "invalid service (empty string)")
            }
        }
    }

    fun validateUserUpdate(
        previous: User,
        updated: User
    ) {

        val changedType = previous.documentType != updated.documentType
        val changedValue = previous.documentValue != updated.documentValue
        val changedDocument = changedType || changedValue

        if (changedDocument) {
            documentValidator.validateDocument(updated)
        }

        if (previous.shotDate != updated.shotDate) shotDateValidator.validateShotDate(updated)

        validateServices(updated.services)
    }

    private fun validateCredential(nusp: String, password: String) {
        validateNuspUniqueness(nusp)
        validatePasswordStructure(password)
    }

    // TODO: refactor to avoid duplication of information (newNUSP, newPassword, newPWHash)
    fun validateEmployeeUpdate(
        previous: Employee,
        updated: Employee,
        newNUSP: String,
        newPassword: String,
        newPasswordHash: String
    ) {

        val changedType = previous.documentType != updated.documentType
        val changedValue = previous.documentValue != updated.documentValue
        val changedDocument = changedType || changedValue

        if (changedDocument) {
            documentValidator.validateDocument(updated)
        }

        if (previous.shotDate != updated.shotDate) shotDateValidator.validateShotDate(updated)

        val changedNUSP = previous.credential.nusp != newNUSP
        val changedPassword = previous.credential.passwordHash != newPasswordHash
        val changedCredential = changedNUSP || changedPassword

        if (changedCredential) {
            validateCredential(newNUSP, newPassword)
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