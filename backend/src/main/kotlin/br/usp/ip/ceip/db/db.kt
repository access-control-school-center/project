package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.Credentials
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun conn() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ceip",
        driver = "org.postgresql.Driver",
        user = "root",
        password = "secret"
    )

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Credentials)
    }
}