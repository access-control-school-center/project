package br.usp.ip.ceip.db

import br.usp.ip.ceip.db.tables.Credentials
import br.usp.ip.ceip.domain.Credential
import br.usp.ip.ceip.domain.CredentialRepository
import br.usp.ip.ceip.domain.exceptions.CredentialNotFoundException
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CredentialRepositoryImpl : CredentialRepository {

    override fun findOneByNusp(nusp: String): Credential {
        var credentials = listOf<Credential>()

        transaction {
            credentials = Credentials
                .select { Credentials.nusp eq nusp }
                .limit(1)
                .map {
                    Credential(
                        nusp = it[Credentials.nusp],
                        passwordHash = it[Credentials.passwordHash]
                    )
                }
        }

        if (credentials.isEmpty())
            throw CredentialNotFoundException(nusp)

        return credentials[0]
    }

}