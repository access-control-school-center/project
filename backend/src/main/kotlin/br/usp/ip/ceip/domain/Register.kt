package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

fun register(person: Person, personRepository: PersonRepository) : Person {
    when (person.documentType) {
        "RG" -> {
            try {
                personRepository.findOneByRG(person.documentValue)

                throw Exception("Person [$person.documentType=$person.documentValue] already registers")
            } catch (e: PersonNotFoundException) {
                return personRepository.save(person)
            }
        }

        "CPF" -> {
            try {
                personRepository.findOneByCPF(person.documentValue)

                throw Exception("Person [$person.documentType=$person.documentValue] already registers")
            } catch (e: PersonNotFoundException) {
                return personRepository.save(person)
            }
        }

        else -> throw Exception("Person must have either RG or CPF")
    }
}