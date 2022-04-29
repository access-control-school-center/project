package br.usp.ip.ceip.domain

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
        credential
    )

    personValidator.validateUpdate(previousEmployee, updatedEmployee)
    return personRepository.save(updatedEmployee)
}