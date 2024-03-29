package br.usp.ip.ceip.domain

import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException

interface PersonRepository {
    /**
     * Finds a person by document type RG
     *
     * @param rg: String - the value of the document type RG
     * @throws PersonNotFoundException when no such document value is found
     * @return the person found
     */
    fun findOneByRG(rg: String): Person

    fun findOneEmployeeByRG(rg: String): Employee

    fun findOneUserByRG(rg: String): User

    /**
     * Finds a person by document type CPF
     *
     * @param cpf: String - the value of the document type CPF
     * @throws PersonNotFoundException when no such document value is found
     * @return the person found
     */
    fun findOneByCPF(cpf: String): Person

    fun findOneEmployeeByCPF(cpf: String): Employee

    fun findOneUserByCPF(cpf: String): User

    /**
     * Finds a person by CEIP-ID
     *
     * @param id: String - the value of the CEIP-ID
     * @throws PersonNotFoundException when no such document value is found
     * @return the person found
     */
    fun findOneById(id: String): Person

    fun findOneEmployeeById(id: String): Employee

    fun findOneUserById(id: String): User

    /**
     * Finds a list of people by name
     *
     * @param name: String - the name
     * @return a list with all matches
     */
    fun findByName(name: String): List<Person>

    fun findUsersByName(name: String): List<User>

    fun findEmployeesByName(name: String): List<Employee>

    /**
     * Saves one user to the repository and gives it a unique ID
     *
     * @param user: User - the representation of the User to be added
     * @return the user saved, with a unique ID
     */
    fun save(user: User): User

    /**
     * Saves one employee to the repository and gives it a unique ID
     *
     * @param employee: Employee - the representation of the Employee to be added
     * @return the Employee saved, with a unique ID
     */
    fun save(employee: Employee): Employee
}