package br.usp.ip.ceip.domain.security

import br.usp.ip.ceip.domain.exceptions.TokenNotFoundException

interface RefreshTokenRepository {

    /**
     * Checks if given refresh token is valid yet
     *
     * @param token: String - the token to be checked
     * @return Boolean - the validity of the given token
     */
    fun isValid(token: String): Boolean

    /**
     * Saves a new refresh token into the repository or ensures it to be valid if it already exists
     *
     * @param token: String - the refresh token
     */
    fun save(token: String)

    /**
     * Makes the given token invalid
     *
     * @param token: String - the token to be made invalid
     * @throws TokenNotFoundException in case such token does not exist
     */
    fun makeInvalid(token: String)

}