package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object RefreshTokens : IntIdTable() {
    val token = varchar("token", 255).uniqueIndex()
}