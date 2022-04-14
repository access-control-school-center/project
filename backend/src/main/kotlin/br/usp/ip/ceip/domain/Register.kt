package br.usp.ip.ceip.domain

fun register(
    user: User,
    personRepository: PersonRepository,
    personValidator: PersonValidator
) : User {
    personValidator.validateCreation(user)
    return personRepository.save(user)
}