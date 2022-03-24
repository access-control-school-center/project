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

    /**
     * Finds a person by document type CPF
     *
     * @param cpf: String - the value of the document type CPF
     * @throws PersonNotFoundException when no such document value is found
     * @return the person found
     */
    fun findOneByCPF(cpf: String): Person

    /**
     * Saves one person to the repository and gives it a unique ID
     *
     * @param person: Person - the representation of the person to be added
     * @return the person saved, with a unique ID
     */
    fun save(person: Person): Person
}