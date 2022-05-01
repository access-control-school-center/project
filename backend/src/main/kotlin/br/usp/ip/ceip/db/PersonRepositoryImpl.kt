package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.*
import br.usp.ip.ceip.db.tables.People.documentType
import br.usp.ip.ceip.db.tables.People.documentValue
import br.usp.ip.ceip.db.tables.People.name
import br.usp.ip.ceip.db.tables.People.shotDate
import br.usp.ip.ceip.domain.*
import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException
import br.usp.ip.ceip.utils.dateStringToLocalDate
import br.usp.ip.ceip.utils.dateToString
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PersonRepositoryImpl : PersonRepository {
    override fun findOneByRG(rg: String) = try {
        findOneUserByRG(rg)
    } catch (e: PersonNotFoundException) {
        findOneEmployeeByRG(rg)
    }

    override fun findOneEmployeeByRG(rg: String): Employee {
        val people = transaction {
            ((People innerJoin Employees) innerJoin Credentials)
                .slice(People.id, name, documentType, documentValue, shotDate, Credentials.nusp, Credentials.passwordHash)
                .select { documentType eq "RG" and (documentValue eq rg) }
                .map {
                    Employee(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value),
                        credential = Credential(nusp = it[Credentials.nusp], passwordHash = it[Credentials.passwordHash])
                    )
                }
        }

        if (people.isEmpty())
            throw PersonNotFoundException("RG", rg)

        return people[0]
    }

    override fun findOneUserByRG(rg: String): User {
        val people = transaction {
            val people = (People innerJoin Users)
                .select { documentType eq "RG" and (documentValue eq rg) }
                .limit(1)
                .map {
                    User(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }

            if (people.isNotEmpty()) {
                val person = people[0]

                val services = (Users innerJoin UserServices)
                    .slice(UserServices.name)
                    .select { Users.personId eq person.id!!.toInt() }
                    .map { it[name] }
                    .toSet()

                listOf(
                    User(
                        name = person.name,
                        documentType = person.documentType,
                        documentValue = person.documentValue,
                        shotDate = person.shotDate,
                        id = person.id,
                        services = services
                    )
                )
            } else {
                listOf()
            }
        }

        if (people.isEmpty()) {
            throw PersonNotFoundException("RG", rg)
        }

        return people[0]
    }

    override fun findOneByCPF(cpf: String) = try {
        findOneUserByCPF(cpf)
    } catch (e: PersonNotFoundException) {
        findOneEmployeeByCPF(cpf)
    }

    override fun findOneEmployeeByCPF(cpf: String): Employee {
        val people = transaction {
            ((People innerJoin Employees) innerJoin Credentials)
                .slice(People.id, name, documentType, documentValue, shotDate, Credentials.nusp, Credentials.passwordHash)
                .select { documentType eq "CPF" and (documentValue eq cpf) }
                .map {
                    Employee(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value),
                        credential = Credential(nusp = it[Credentials.nusp], passwordHash = it[Credentials.passwordHash])
                    )
                }
        }

        if (people.isEmpty())
            throw PersonNotFoundException("CPF", cpf)

        return people[0]
    }

    override fun findOneUserByCPF(cpf: String): User {
        val people = transaction {
            val people = (People innerJoin Users)
                .select { documentType eq "CPF" and (documentValue eq cpf) }
                .limit(1)
                .map {
                    User(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }

            if (people.isNotEmpty()) {
                val person = people[0]

                val services = (Users innerJoin UserServices)
                    .slice(UserServices.name)
                    .select { Users.personId eq person.id!!.toInt() }
                    .map { it[name] }
                    .toSet()

                listOf(
                    User(
                        name = person.name,
                        documentType = person.documentType,
                        documentValue = person.documentValue,
                        shotDate = person.shotDate,
                        id = person.id,
                        services = services
                    )
                )
            } else {
                listOf()
            }
        }

        if (people.isEmpty()) {
            throw PersonNotFoundException("CPF", cpf)
        }

        return people[0]
    }

    override fun findOneById(id: String) = try {
        findOneUserById(id)
    } catch (e: PersonNotFoundException) {
        findOneEmployeeById(id)
    }

    override fun findOneEmployeeById(id: String): Employee {
        val intID = CEIPID.fromHexString(id).toInt()

        val people = transaction {
            ((People innerJoin Employees) innerJoin Credentials)
                .slice(People.id, name, documentType, documentValue, shotDate, Credentials.nusp, Credentials.passwordHash)
                .select { People.id eq intID }
                .map {
                    Employee(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value),
                        credential = Credential(nusp = it[Credentials.nusp], passwordHash = it[Credentials.passwordHash])
                    )
                }
        }

        if (people.isEmpty())
            throw PersonNotFoundException("CEIPID", id)

        return people[0]
    }

    override fun findOneUserById(id: String): User {
        val intID = CEIPID.fromHexString(id).toInt()

        val people = transaction {
            val people = (People innerJoin Users)
                .select { People.id eq intID }
                .limit(1)
                .map {
                    User(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }

            val person = people[0]

            val services = (Users innerJoin UserServices)
                .slice(UserServices.name)
                .select { Users.personId eq person.id!!.toInt() }
                .map { it[name] }
                .toSet()

            listOf(User(
                name = person.name,
                documentType = person.documentType,
                documentValue = person.documentValue,
                shotDate = person.shotDate,
                id = person.id,
                services = services
            ))
        }

        if (people.isEmpty())
            throw PersonNotFoundException("CEIPID", id)

        return people[0]
    }

    override fun findByName(name: String) = findUsersByName(name) + findEmployeesByName(name)

    override fun findUsersByName(name: String): List<User> {
        return transaction {
            val people = (People innerJoin Users)
                .select { People.name like "%$name%" }
                .map {
                    User(
                        name = it[People.name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }

            people.map { person ->
                val services = (Users innerJoin UserServices)
                    .slice(UserServices.name)
                    .select { Users.personId eq person.id!!.toInt() }
                    .map { it[UserServices.name] }
                    .toSet()

                User(
                    name = person.name,
                    documentType = person.documentType,
                    documentValue = person.documentValue,
                    shotDate = person.shotDate,
                    id = person.id,
                    services = services
                )
            }
        }
    }

    override fun findEmployeesByName(name: String) = transaction {
        ((People innerJoin Employees) innerJoin Credentials)
            .slice(People.id, People.name, documentType, documentValue, shotDate, Credentials.nusp, Credentials.passwordHash)
            .select { People.name like "%$name%" }
            .map {
                Employee(
                    name = it[People.name],
                    documentType = it[documentType],
                    documentValue = it[documentValue],
                    shotDate = dateStringToLocalDate(it[shotDate]),
                    id = CEIPID.fromInt(it[People.id].value),
                    credential = Credential(nusp = it[Credentials.nusp], passwordHash = it[Credentials.passwordHash])
                )
            }
    }

    override fun save(employee: Employee): Employee {
        return if (employee.id == null) store(employee)
        else update(employee)
    }

    private fun update(employee: Employee): Employee {
        transaction {
            People.update({ People.id eq employee.id!!.toInt() }) {
                it[name] = employee.name
                it[documentType] = employee.documentType
                it[documentValue] = employee.documentValue
                it[shotDate] = dateToString(employee.shotDate)
            }

            val credId = Employees
                .select { Employees.personId eq employee.id!!.toInt() }
                .map { it[Employees.credentialId] }
                .get(0)

            Credentials.update({ Credentials.id eq credId }) {
                it[nusp] = employee.credential.nusp
                it[passwordHash] = employee.credential.passwordHash
            }
        }

        return Employee(
            name = employee.name,
            documentType = employee.documentType,
            documentValue = employee.documentValue,
            shotDate = employee.shotDate,
            id = employee.id,
            credential = employee.credential
        )
    }

    private fun store(employee: Employee): Employee {
        val id = transaction {
            val cId = Credentials.insertAndGetId {
                it[nusp] = employee.credential.nusp
                it[passwordHash] = employee.credential.passwordHash
            }

            val pId = People.insertAndGetId {
                it[name] = employee.name
                it[documentType] = employee.documentType
                it[documentValue] = employee.documentValue
                it[shotDate] = dateToString(employee.shotDate)
            }

            Employees.insert {
                it[personId] = pId
                it[credentialId] = cId
            }

            pId
        }

        return Employee(
            name = employee.name,
            documentType = employee.documentType,
            documentValue = employee.documentValue,
            shotDate = employee.shotDate,
            id = CEIPID.fromInt(id.value),
            credential = employee.credential
        )
    }

    override fun save(user: User): User {
        return if (user.id == null) store(user)
        else update(user)
    }

    private fun update(user: User): User {
        transaction {
            People.update({ People.id eq user.id!!.toInt() }) {
                it[name] = user.name
                it[documentType] = user.documentType
                it[documentValue] = user.documentValue
                it[shotDate] = dateToString(user.shotDate)
            }

            val uId = Users
                .select { Users.personId eq user.id!!.toInt() }
                .map { it[Users.id] }
                .get(0)

            val storedServices = UserServices
                .select { UserServices.userId eq uId }
                .map { it[UserServices.name] }

            user.services
                .filter { it !in storedServices }
                .forEach { svc ->
                    UserServices.insert {
                        it[name] = svc
                        it[userId] = uId
                    }
                }
        }

        return User(
            name = user.name,
            documentType = user.documentType,
            documentValue = user.documentValue,
            shotDate = user.shotDate,
            id = user.id,
            services = user.services
        )
    }

    private fun store(user: User): User {
        val id = transaction {
            val pId = People.insertAndGetId {
                it[name] = user.name
                it[documentType] = user.documentType
                it[documentValue] = user.documentValue
                it[shotDate] = dateToString(user.shotDate)
            }

            val uId = Users.insertAndGetId { it[personId] = pId }

            user.services.forEach { svc ->
                UserServices.insert {
                    it[name] = svc
                    it[userId] = uId
                }
            }

            pId
        }

        return User(
            name = user.name,
            documentType = user.documentType,
            documentValue = user.documentValue,
            shotDate = user.shotDate,
            id = CEIPID.fromInt(id.value),
            services = user.services
        )
    }
}