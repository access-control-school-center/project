package br.usp.ip.ceip.domain

interface CredentialRepository {
    fun findOneByNusp(nusp: String): Credential
}