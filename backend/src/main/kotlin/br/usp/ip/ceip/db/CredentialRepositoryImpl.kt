package br.usp.ip.ceip.db

import at.favre.lib.crypto.bcrypt.BCrypt
import br.usp.ip.ceip.domain.Credential
import br.usp.ip.ceip.domain.CredentialRepository

class CredentialRepositoryImpl : CredentialRepository {
    private val relation = mutableSetOf<Credential>()

    init {
        val cred = Credential(
            nusp = "1234",
            passwordHash = BCrypt.withDefaults().hashToString(12, "1234".toCharArray())
        )
        relation.add(cred)
    }

    override fun findOneByNusp(nusp: String): Credential {
        return relation.find { it.nusp == nusp }
            ?: throw Exception("Credential not found")
    }

}