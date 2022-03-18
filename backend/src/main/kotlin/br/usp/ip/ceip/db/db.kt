package br.usp.ip.ceip.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Cities: IntIdTable() {
    val name = varchar("name", 50)
}

fun conn() {
    Database.connect(
        url ="jdbc:postgresql://localhost:5432/ceip",
        driver = "org.postgresql.Driver",
        user = "root",
        password = "secret"
    )

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities)

        Cities.insert {
            it[name] = "St. Petersburg"
        } get Cities.id

        println("---> Cities:")
        for (city in Cities.selectAll())
            println(city)
    }
}