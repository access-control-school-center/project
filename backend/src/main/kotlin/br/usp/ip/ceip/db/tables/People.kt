package br.usp.ip.ceip.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object People : IntIdTable() {
    val name = varchar("name", 255)
    val documentType = varchar("document_type", 255)
    val documentValue = varchar("document_value", 255)
    val shotDate = varchar("shot_date", 255)

    init {
        uniqueIndex(documentType, documentValue)
    }
}