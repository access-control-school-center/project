package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.CredentialNotFoundException
import br.usp.ip.ceip.domain.exceptions.ValidationException

class EmployeeValidator (
    private val personRepository: PersonRepository,
    private val documentValidator: DocumentValidator,
    private val shotDateValidator: ShotDateValidator,
    private val credentialRepository: CredentialRepository
) : PersonValidator (
    personRepository,
    documentValidator,
    shotDateValidator) {

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
    private fun validateCredential(nusp: String, password: String) {
        validateNuspUniqueness(nusp)
        validatePasswordStructure(password)
    }

    // TODO: refactor to avoid duplication of information (newNUSP, newPassword, newPWHash)
    fun validateUpdate(
        previous: Employee,
        updated: Employee,
        newNUSP: String,
        newPassword: String,
        newPasswordHash: String
    ) {

        super.validateUpdate(previous, updated)

        val changedNUSP = previous.credential.nusp != newNUSP
        val changedPassword = previous.credential.passwordHash != newPasswordHash
        val changedCredential = changedNUSP || changedPassword

        if (changedCredential) {
            validateCredential(newNUSP, newPassword)
        }
    }
}