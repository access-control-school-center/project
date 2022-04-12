package br.usp.ip.ceip.domain

class DocumentValidator {
    private fun removeInvalidCharacters(value: String) : String {
        var cpf: String = ""
        for (c in value) {
            if (c.isDigit()) cpf += c
        }

        return cpf
    }

    private fun validateFirstDigit(cpf: String) : Boolean {
        var sum: Int = 0
        var rest: Int

        for (i in 1..9) {
            sum += cpf.get(i-1).toString().toInt() * (11 - i)
        }

        rest = ( sum * 10 ) % 11

        if (rest == 10) rest = 0

        if (rest != cpf.get(9).toString().toInt()) return false

        return true
    }

    private fun validateSecondDigit(cpf: String) : Boolean {
        var sum: Int = 0
        var rest: Int

        for (i in 1..10) {
            sum += cpf.get(i-1).toString().toInt() * (12 - i)
        }

        rest = ( sum * 10) % 11

        if (rest == 10) rest = 0

        if (rest != cpf.get(10).toString().toInt()) return false

        return true
    }

    private fun validateCPF(value: String) : Boolean {
        val cpf: String = removeInvalidCharacters(value)

        if (cpf.length != 11) return false;

        return validateFirstDigit(cpf) && validateSecondDigit(cpf)
    }

    fun validateDocument(person: Person) : Boolean{
        return validateCPF(person.documentValue)
    }
}