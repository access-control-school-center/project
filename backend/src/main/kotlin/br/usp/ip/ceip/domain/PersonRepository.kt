package br.usp.ip.ceip.domain

interface PersonRepository {

    /** adds a new person or update an existing one based on the id
     * @param person: Person - the person to be saved
     * @return Person - the representation of the person stored
     * @throws InvalidPersonException when there are validation errors
     */
    fun save(person: Person): Person

    /** finds the person with the given id
     * @param id: String - the given id
     * @return Person - the person found
     * @throws PersonNotFoundException when no such id exist
     */
    fun getById(id: String): Person

    /** finds the person with the given document
     * @param type: DocumentType - the type of the given document
     * @param value: String - the value of the given document
     * @return Person - the person found
     * @throws PersonNotFoundException when no such document exist
     */
    fun getByDocument(type: DocumentType, value: String): Person

    /** finds the person with the given NUSP as credential
     * @param nusp: String - the given NUSP
     * @return Person - the person found
     * @throws PersonNotFoundException when no such document exist
     */
    fun getByNusp(nusp: String): Person

    /** finds all the people with the given name
     * @param id: String - the given id
     * @return List<Person> - the people found; if no one is found, then an empty list is returned
     */
    fun findAllByName(name: String): List<Person>

    /** removes the person with the given id
     * @param id: String - the given id
     * @return Person - the person removed
     * @throws PersonNotFoundException when no such id exist
     */
    fun removeOne(id: String): Person

    /** removes the person that matches with the given person
     * @param person: Person - the given person
     * @return Person - the person removed
     * @throws PersonNotFoundException when no such person exist
     */
    fun removeOne(person: Person): Person

}