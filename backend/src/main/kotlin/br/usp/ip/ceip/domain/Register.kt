package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.Person

fun register(person: Person, personRepository: PersonRepository) : Person {

    if (person.documentType.compareTo("RG") == 0) {
        try {
            personRepository.findOneByRG(person.documentValue)

            throw Exception("Person [$person.documentType=$person.documentValue] already registers")
        } catch (e: Exception) {
            personRepository.save(person)
        }
    }

    if (person.documentType.compareTo("CPF") == 0) {
        try {
            personRepository.findOneByCPF(person.documentValue)

            throw Exception("Person [$person.documentType=$person.documentValue] already registers")
        } catch (e: Exception) {
            personRepository.save(person)
        }
    }

    return person
}