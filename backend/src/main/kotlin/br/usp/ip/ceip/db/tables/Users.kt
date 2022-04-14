package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val personId = reference("person_id", People.id).uniqueIndex()
}