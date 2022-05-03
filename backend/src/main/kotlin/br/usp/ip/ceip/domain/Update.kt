package br.usp.ip.ceip.domain

import at.favre.lib.crypto.bcrypt.BCrypt
import br.usp.ip.ceip.api.EmployeeUpdatePayload

fun update (
    id: String,
    employeeUpdatePayload: EmployeeUpdatePayload,
    personRepository: PersonRepository,
    personValidator: PersonValidator
) : Employee {

    val previousEmployee = personRepository.findOneEmployeeById(id)
    val (name, documentType, documentValue, shotDate, credential) = employeeUpdatePayload


    val updatedEmployee = Employee (
        name,
        documentType,
        documentValue,
        shotDate,
        CEIPID(id),
        Credential(
            nusp = credential.nusp,
            passwordHash = BCrypt.withDefaults().hashToString(12, credential.password.toCharArray())
        )
    )

    personValidator.validateUpdate(previousEmployee, updatedEmployee)
    return personRepository.save(updatedEmployee)
}