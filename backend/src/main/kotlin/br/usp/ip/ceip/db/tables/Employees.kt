package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Employees : IntIdTable() {
    val personId = reference("person_id", People.id).uniqueIndex()
    val credentialId = reference("credential_id", Credentials.id).uniqueIndex()
}