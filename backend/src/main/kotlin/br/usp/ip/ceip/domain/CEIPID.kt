package br.usp.ip.ceip.domain

import kotlinx.serialization.Serializable

@Serializable(with = CEIPIDSerializer::class)
class CEIPID(val id: String) {
    companion object {
        private const val BASE = 712883

        fun fromInt(n: Int): CEIPID {
            val id = Integer.toHexString(n + BASE)
            return CEIPID(id)
        }

        fun fromHexString(hex: String): CEIPID {
            return CEIPID(hex)
        }
    }

    fun toInt(): Int {
        val n = id.toInt(16)

        return n - BASE
    }

    override fun toString(): String {
        return id
    }
}