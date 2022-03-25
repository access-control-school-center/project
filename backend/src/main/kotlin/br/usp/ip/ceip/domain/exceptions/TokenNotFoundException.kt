package br.usp.ip.ceip.domain.exceptions

class TokenNotFoundException(
    token: String
) : Exception("Token $token is not found") {
}