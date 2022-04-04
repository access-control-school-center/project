package br.usp.ip.ceip.domain.exceptions

class ValidationException(
    className: String,
    attribute: String,
    errorMessage: String,
) : Exception("$className validation failed of '$attribute': $errorMessage")