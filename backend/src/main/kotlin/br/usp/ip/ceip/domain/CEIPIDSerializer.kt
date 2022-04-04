package br.usp.ip.ceip.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CEIPIDSerializer : KSerializer<CEIPID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CEIPID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): CEIPID {
        val id = decoder.decodeString()
        return CEIPID(id)
    }

    override fun serialize(encoder: Encoder, value: CEIPID) {
        encoder.encodeString(value.id)
    }
}