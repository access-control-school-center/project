package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.ValidationException

class DocumentValidator {
    private fun removeInvalidCharactersCPF(value: String) : String {
        var cpf: String = ""
        for (c in value) {
            if (c.isDigit()) cpf += c
        }

        return cpf
    }

    private fun removeInvalidCharactersRG(value: String) : String {
        var cpf: String = ""
        for (c in value) {
            if (c.isLetterOrDigit()) cpf += c
        }

        return cpf
    }

    private fun validateFirstDigit(cpf: String) {
        var sum: Int = 0
        var rest: Int

        for (i in 1..9) {
            sum += cpf[i-1].toString().toInt() * (11 - i)
        }

        rest = ( sum * 10 ) % 11

        if (rest == 10) rest = 0

        if (rest != cpf[9].toString().toInt()) {
            throw ValidationException("Person", "document [CPF]", "invalid document value")
        }
    }

    private fun validateSecondDigit(cpf: String) {
        var sum: Int = 0
        var rest: Int

        for (i in 1..10) {
            sum += cpf[i-1].toString().toInt() * (12 - i)
        }

        rest = ( sum * 10) % 11

        if (rest == 10) rest = 0

        if (rest != cpf[10].toString().toInt()) {
            throw ValidationException("Person", "document [CPF]", "invalid document value")
        }
    }

    private fun validateCPF(value: String) {
        val cpf: String = removeInvalidCharactersCPF(value)

        if (cpf.length != 11) {
            throw ValidationException("Person", "document [CPF]", "invalid document value")
        }

        validateFirstDigit(cpf)
        validateSecondDigit(cpf)
    }

    private fun validateRG(value: String) {
        val rg = removeInvalidCharactersCPF(value)

        if (rg.length < 7 || rg.length > 10) {
            throw ValidationException("Person", "document [RG]", "invalid document value")
        }
    }

    fun validateDocument(person: Person) {
        when (person.documentType) {
            "RG" -> {
                validateRG(person.documentValue)
            }
            "CPF" -> {
                validateCPF(person.documentValue)
            }
            "Não Documentado" -> {
                return
            }
        }
    }
}