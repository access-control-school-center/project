package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.People
import br.usp.ip.ceip.db.tables.People.documentType
import br.usp.ip.ceip.db.tables.People.documentValue
import br.usp.ip.ceip.db.tables.People.name
import br.usp.ip.ceip.db.tables.People.shotDate
import br.usp.ip.ceip.domain.CEIPID
import br.usp.ip.ceip.domain.Person
import br.usp.ip.ceip.domain.PersonRepository
import br.usp.ip.ceip.domain.exceptions.PersonNotFoundException
import br.usp.ip.ceip.utils.dateStringToLocalDate
import br.usp.ip.ceip.utils.dateToString
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PersonRepositoryImpl : PersonRepository {
    override fun findOneByRG(rg: String): Person {
        val people = transaction {
            People
                .select { documentType eq "RG" and (documentValue eq rg) }
                .limit(1)
                .map {
                    Person(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }
        }

        if (people.isEmpty()) {
            throw PersonNotFoundException("RG", rg)
        }

        return people[0]
    }

    override fun findOneByCPF(cpf: String): Person {
        val people = transaction {
            People
                .select { documentType eq "CPF" and (documentValue eq cpf) }
                .limit(1)
                .map {
                    Person(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
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
            People
                .select { People.id eq intID }
                .limit(1)
                .map {
                    Person(
                        name = it[name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }
        }

        if (people.isEmpty())
            throw PersonNotFoundException("CEIPID", id)

        return people[0]
    }

    override fun findByName(name: String): List<Person> {
        return transaction {
            People
                .select { People.name like "%$name%" }
                .map {
                    Person(
                        name = it[People.name],
                        documentType = it[documentType],
                        documentValue = it[documentValue],
                        shotDate = dateStringToLocalDate(it[shotDate]),
                        id = CEIPID.fromInt(it[People.id].value)
                    )
                }
        }
    }

    override fun save(person: Person): Person {
        if (person.documentValue.isEmpty()) {
            throw Exception("Invalid document value")
        }

        val id = transaction {
            People.insertAndGetId {
                it[name] = person.name
                it[documentType] = person.documentType
                it[documentValue] = person.documentValue
                it[shotDate] = dateToString(person.shotDate)
            }
        }

        val (name, documentType, documentValue, shotDate) = person

        return Person(
            name = name,
            documentType = documentType,
            documentValue = documentValue,
            shotDate = shotDate,
            id = CEIPID.fromInt(id.value)
        )
    }
}