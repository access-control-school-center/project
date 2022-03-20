package br.usp.ip.ceip.domain

import java.util.Date

enum class Role {
    UserOrCompanion,
    AccessController,
    Employee,
    Student,
    Volunteer,
}

class Person(
    val name: String,
    val role: Role
) {
    companion object {
        // STATEFUL vs. STATELESS
        var n: Int = 0
        private val initial = 43189

        fun generateNextID(): String {
            n++
            val numId = initial + n
            return Integer.toHexString(numId)
        }
    }

    val id: String
    lateinit var document: Document
    lateinit var lastShotDate: Date
    lateinit var credential: Credential
    lateinit var responsible: Responsible
    lateinit var services: Set<String>

    init {
        id = Person.generateNextID()
    }
}
