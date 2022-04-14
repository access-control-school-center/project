package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun setupDatabase(
    url: String,
    driver: String,
    user: String,
    password: String,
) {

    Database.connect(url, driver, user, password)

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Credentials)
        SchemaUtils.create(RefreshTokens)
        SchemaUtils.create(People)
        SchemaUtils.create(Users)
        SchemaUtils.create(UserServices)
    }
}