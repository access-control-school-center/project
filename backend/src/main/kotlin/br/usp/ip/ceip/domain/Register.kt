package br.usp.ip.ceip.domain

fun register(
    person: Person,
    personRepository: PersonRepository,
    personValidator: PersonValidator
) : Person {
    personValidator.validateCreation(person)
    return personRepository.save(person)
}