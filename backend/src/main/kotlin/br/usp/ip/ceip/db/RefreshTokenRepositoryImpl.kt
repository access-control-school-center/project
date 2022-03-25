package br.usp.ip.ceip.db

import br.usp.ip.ceip.domain.exceptions.TokenNotFoundException
import br.usp.ip.ceip.domain.security.RefreshTokenRepository

class RefreshTokenRepositoryImpl : RefreshTokenRepository {

    private val relation = mutableMapOf<String, Boolean>()

    override fun isValid(token: String): Boolean {
        return relation[token] ?: false
    }

    override fun save(token: String) {
        relation[token] = true
    }

    override fun makeInvalid(token: String) {
        if (relation[token] == null) {
            throw TokenNotFoundException(token)
        }

        relation[token] = false
    }
}