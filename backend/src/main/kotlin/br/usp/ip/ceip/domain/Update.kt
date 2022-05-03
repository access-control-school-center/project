package br.usp.ip.ceip.domain

import at.favre.lib.crypto.bcrypt.BCrypt
import br.usp.ip.ceip.api.EmployeeUpdatePayload

fun update (
    id: String,
    employeeUpdatePayload: EmployeeUpdatePayload, // TODO: refactor to avoid using external abstraction inside the domain
    personRepository: PersonRepository,
    personValidator: PersonValidator
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

    personValidator.validateUpdate(previousEmployee, updatedEmployee, credential.nusp, credential.password, passwordHash)
    return personRepository.save(updatedEmployee)
}