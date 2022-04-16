package br.usp.ip.ceip.domain.exceptions

class CredentialNotFoundException(
    val nusp: String
): Exception("Credential nusp=$nusp not found") {
}