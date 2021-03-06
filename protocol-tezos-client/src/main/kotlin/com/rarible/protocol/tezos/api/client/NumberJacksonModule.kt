package com.rarible.protocol.tezos.api.client

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal
import java.math.BigInteger

object NumberJacksonModule : SimpleModule() {

    init {
        addSerializer(BigDecimalSerializer)
        addSerializer(ToStringSerializer(BigInteger::class.java))
    }

    private object BigDecimalSerializer : StdScalarSerializer<BigDecimal>(BigDecimal::class.java) {

        override fun serialize(value: BigDecimal, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeString(value.stripTrailingZeros().toPlainString())
        }
    }

}