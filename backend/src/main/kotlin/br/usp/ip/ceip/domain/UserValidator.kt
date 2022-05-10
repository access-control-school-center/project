package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.ValidationException

class UserValidator(
    private val personRepository: PersonRepository,
    private val documentValidator: DocumentValidator,
    private val shotDateValidator: ShotDateValidator,
) : PersonValidator(
    personRepository,
    documentValidator,
    shotDateValidator) {

    private fun validateServices(services: Set<String>) {
        for (s in services) {
            if (s.isEmpty()) {
                throw ValidationException("User", "services", "invalid service (empty string)")
            }
        }
    }

    fun validateUpdate(previous: User, updated: User) {
        super.validateUpdate(previous, updated)
        validateServices(updated.services)
    }
}