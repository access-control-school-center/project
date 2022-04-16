package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

enum class Params(val key: String) {
    DOC_TYPE("document.type"),
    DOC_VALUE("document.value"),
    NAME("name"),
    CEIPID("id")
}

fun searchUsers(params: Map<Params, String?>, personRepository: PersonRepository): List<User> {
    if (params[Params.DOC_TYPE] != null && params[Params.DOC_VALUE] != null) {
        val type = params[Params.DOC_TYPE]!!
        val value = params[Params.DOC_VALUE]!!

        return try {
            when (type) {
                "RG" -> listOf(personRepository.findOneUserByRG(value))
                "CPF" -> listOf(personRepository.findOneUserByCPF(value))
                else -> emptyList()
            }
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.CEIPID] != null) {
        return try {
            listOf(personRepository.findOneUserById(params[Params.CEIPID]!!))
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.NAME] != null) {
        return personRepository.findUsersByName(params[Params.NAME]!!)
    } else {
        return emptyList()
    }
}

fun searchEmployees(params: Map<Params, String?>, personRepository: PersonRepository): List<Employee> {
    if (params[Params.DOC_TYPE] != null && params[Params.DOC_VALUE] != null) {
        val type = params[Params.DOC_TYPE]!!
        val value = params[Params.DOC_VALUE]!!

        return try {
            when (type) {
                "RG" -> listOf(personRepository.findOneEmployeeByRG(value))
                "CPF" -> listOf(personRepository.findOneEmployeeByCPF(value))
                else -> emptyList()
            }
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.CEIPID] != null) {
        return try {
            listOf(personRepository.findOneEmployeeById(params[Params.CEIPID]!!))
        } catch (e: PersonNotFoundException) {
            emptyList()
        }
    } else if (params[Params.NAME] != null) {
        return personRepository.findEmployeesByName(params[Params.NAME]!!)
    } else {
        return emptyList()
    }
}
