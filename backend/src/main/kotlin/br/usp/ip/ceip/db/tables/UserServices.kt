package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserServices : IntIdTable() {
    val name = varchar("name", 255)
    val userId = reference("user_id", Users.id)

    init {
        uniqueIndex(name, userId)
    }
}