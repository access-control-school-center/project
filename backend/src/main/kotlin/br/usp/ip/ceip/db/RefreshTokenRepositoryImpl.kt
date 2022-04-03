package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.RefreshTokens
import br.usp.ip.ceip.domain.exceptions.TokenNotFoundException
import br.usp.ip.ceip.domain.security.RefreshTokenRepository
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class RefreshTokenRepositoryImpl : RefreshTokenRepository {

    override fun isValid(token: String): Boolean {
        var count = 0L

        transaction {
            count = RefreshTokens
                .select { RefreshTokens.token eq token }
                .limit(1)
                .count()
        }

        return count == 1L
    }

    override fun save(token: String) {
        transaction {
            RefreshTokens.insert {
                it[RefreshTokens.token] = token
            }
        }
    }

    override fun makeInvalid(token: String) {
        var removed = 0

        transaction {
            removed = RefreshTokens.deleteWhere {
                RefreshTokens.token eq token
            }
        }

        if (removed == 0) {
            throw TokenNotFoundException(token)
        }
    }
}