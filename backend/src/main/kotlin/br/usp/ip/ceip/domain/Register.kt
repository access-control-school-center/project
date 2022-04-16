package br.usp.ip.ceip.domain

import at.favre.lib.crypto.bcrypt.BCrypt
import java.time.LocalDate

fun register(
    user: User,
    personRepository: PersonRepository,
    personValidator: PersonValidator
) : User {
    personValidator.validateCreation(user)
    return personRepository.save(user)
}

fun register(
    name: String,
    documentType: String,
    documentValue: String,
    shotDate: LocalDate,
    nusp: String,
    password: String,
    personRepository: PersonRepository,
    personValidator: PersonValidator
): Employee {
    personValidator.validatePasswordStructure(password)
    val credential = Credential(
        nusp,
        passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
    )
    val employee = Employee(
        name,
        documentType,
        documentValue,
        shotDate,
        credential = credential
    )
    personValidator.validateCreation(employee)
    return personRepository.save(employee)
}