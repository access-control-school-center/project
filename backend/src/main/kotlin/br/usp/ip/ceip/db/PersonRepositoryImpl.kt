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
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PersonRepositoryImpl : PersonRepository {
    override fun findOneByRG(rg: String): Person {
        val people = transaction {
            val people = People
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

    override fun findOneByCPF(cpf: String): Person {
        val people = transaction {
            val people = People
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

    override fun findOneById(id: String): Person {
        val intID = CEIPID.fromHexString(id).toInt()

        val people = transaction {
            val people = People
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

    override fun findByName(name: String): List<Person> {
        return transaction {
            val people = People
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
                    .map { it[People.name] }
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

    override fun save(employee: Employee): Employee {
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