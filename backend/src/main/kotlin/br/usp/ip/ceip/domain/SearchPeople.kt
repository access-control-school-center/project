package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

enum class Params(val key: String) {
    DOC_TYPE("document.type"),
    DOC_VALUE("document.value"),
    NAME("name"),
    CEIPID("id")
}

fun searchPeople(params: Map<Params, String?>, personRepository: PersonRepository): List<Person> {
    if (params[Params.DOC_TYPE] != null && params[Params.DOC_VALUE] != null) {
        val type = params[Params.DOC_TYPE]!!
        val value = params[Params.DOC_VALUE]!!

        return try {
            when (type) {
                "RG" -> listOf(personRepository.findOneByRG(value))
                "CPF" -> listOf(personRepository.findOneByCPF(value))
                else -> emptyList()
            }
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.CEIPID] != null) {
        return try {
            listOf(personRepository.findOneById(params[Params.CEIPID]!!))
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.NAME] != null) {
        return personRepository.findByName(params[Params.NAME]!!)
    } else {
        return emptyList()
    }
}