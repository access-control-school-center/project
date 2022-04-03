package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

fun register(person: Person, personRepository: PersonRepository) : Person {
    if (person.documentType.compareTo("RG") == 0) {
        try {
            personRepository.findOneByRG(person.documentValue)

             throw Exception("Person [$person.documentType=$person.documentValue] already registers")
        } catch (e: PersonNotFoundException) {
            personRepository.save(person)
        }
    }

    if (person.documentType.compareTo("CPF") == 0) {
        try {
            personRepository.findOneByCPF(person.documentValue)

             throw Exception("Person [$person.documentType=$person.documentValue] already registers")
        } catch (e: PersonNotFoundException) {
            personRepository.save(person)
        }
    }

    // caso não vem nem com RG nem com CPF

    return person
}