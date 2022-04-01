package br.usp.ip.ceip.db

import br.usp.ip.ceip.domain.Person
import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

@Suppress("unused")
class PersonRepositoryImpl : PersonRepository {

    private val rgIndexedRelation = mutableMapOf<String, Person>()
    private val cpfIndexedRelation = mutableMapOf<String, Person>()

    override fun findOneByRG(rg: String): Person {
        return rgIndexedRelation[rg]
            ?: throw PersonNotFoundException("RG", rg)
    }

    override fun findOneByCPF(cpf: String): Person {
        return cpfIndexedRelation[cpf]
            ?: throw PersonNotFoundException("CPF", cpf)
    }

    override fun save(person: Person): Person {
        if (person.documentValue.isEmpty()) {
            throw Exception("Invalid document value")
        }

        when (person.documentType) {
            "RG" -> rgIndexedRelation.put(person.documentValue, person)
            "CPF" -> cpfIndexedRelation.put(person.documentValue, person)
            else -> {
                throw Exception("Invalid document type")
            }
        }
        return person
    }
}