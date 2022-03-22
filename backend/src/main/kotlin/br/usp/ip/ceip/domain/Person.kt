package br.usp.ip.ceip.domain

import kotlinx.serialization.descriptors.serialDescriptor
import java.util.Date

enum class Role {
    UserOrCompanion,
    Employee,
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

        fun isValid(person: Person): Boolean {
            return when(person.role) {
                Role.Employee -> isEmployeeValid(person)
                Role.UserOrCompanion -> isUserValid(person)
                Role.Volunteer -> isVolunteerValid(person)
            }
        }

        private fun isEmployeeValid(employee: Person): Boolean {
            return try {
                val (nusp, password) = employee.credential
                nusp.isNotEmpty() && password.isNotEmpty()
            } catch (e: UninitializedPropertyAccessException) {
                false
            }
        }

        private fun isUserValid(user: Person): Boolean {
            return try {
                user.services
                true
            } catch (e: UninitializedPropertyAccessException) {
                false
            }
        }

        private fun isVolunteerValid(volunteer: Person): Boolean {
            return try {
                val (name, service) = volunteer.responsible
                name.isNotEmpty() && service.isNotEmpty()
            } catch (e: UninitializedPropertyAccessException) {
                false
            }
        }
    }

    val id: String
    lateinit var document: Document
    lateinit var lastShotDate: Date
    lateinit var credential: Credential
    lateinit var responsible: Responsible
    lateinit var services: MutableSet<String>

    init {
        id = Person.generateNextID()

        if (role == Role.UserOrCompanion)
            services = mutableSetOf<String>()
    }

    fun addCredential(nusp: String, password: String): Credential? {
        if (role != Role.Employee) return null

        credential = Credential(nusp, password)
        return credential
    }

    fun addResponsible(name: String, service: String): Responsible? {
        if (role != Role.Volunteer) return null

        responsible = Responsible(name, service)
        return responsible
    }

    fun addService(service: String): MutableSet<String>? {
        if (role != Role.UserOrCompanion) return null

        if (service.isNotEmpty()) services.add(service)
        return services
    }

    override fun toString(): String {
        return "Person(" +
                "id=$id," +
                "name=$name" +
                ")"
    }
}
