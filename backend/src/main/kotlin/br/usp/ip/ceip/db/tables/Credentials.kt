package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Credentials : IntIdTable() {
    val nusp = varchar("nusp", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
}