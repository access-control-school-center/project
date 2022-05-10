package br.usp.ip.ceip.domain

import at.favre.lib.crypto.bcrypt.BCrypt
import br.usp.ip.ceip.api.EmployeeUpdatePayload
import java.time.LocalDate

fun updateUser (
    id: String,
    name: String,
    documentType: String,
    documentValue: String,
    shotDate: LocalDate,
    services: Set<String>,
    personRepository: PersonRepository,
    userValidator: UserValidator
) : User {

    val previousUser = personRepository.findOneUserById(id)

    val updatedUser = User (
        name,
        documentType,
        documentValue,
        shotDate,
        CEIPID(id),
        services
    )

    userValidator.validateUpdate(previousUser, updatedUser)
    return personRepository.save(updatedUser)

}


fun updateEmployee (
    id: String,
    employeeUpdatePayload: EmployeeUpdatePayload, // TODO: refactor to avoid using external abstraction inside the domain
    personRepository: PersonRepository,
    employeeValidator: EmployeeValidator
) : Employee {

    val previousEmployee = personRepository.findOneEmployeeById(id)
    val (name, documentType, documentValue, shotDate, credential) = employeeUpdatePayload

    val passwordHash = BCrypt.withDefaults().hashToString(12, credential.password.toCharArray())
    val updatedEmployee = Employee (
        name,
        documentType,
        documentValue,
        shotDate,
        CEIPID(id),
        Credential(
            nusp = credential.nusp,
            passwordHash = passwordHash
        )
    )

    employeeValidator.validateUpdate(previousEmployee, updatedEmployee, credential.nusp, credential.password, passwordHash)
    return personRepository.save(updatedEmployee)
}