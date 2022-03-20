package br.usp.ip.ceip.domain

enum class DocumentType {
    RG,
    CPF,
    NUSP,
    ABSENT
}

data class Document(
    val type: DocumentType,
    val value: String
)