package com.rarible.protocol.tezos.subscriber

import com.rarible.core.kafka.json.JsonDeserializer
import com.rarible.protocol.tezos.dto.TezosActivitySafeDto
import com.rarible.protocol.tezos.dto.TezosItemSafeEventDto
import com.rarible.protocol.tezos.dto.TezosOrderSafeEventDto
import com.rarible.protocol.tezos.dto.TezosOwnershipSafeEventDto
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory

// TODO remove later
sealed class SafeJsonDeserializer : JsonDeserializer() {

    private val logger = LoggerFactory.getLogger(javaClass)

    abstract val errorValue: Any

    override fun deserialize(topic: String?, headers: Headers?, data: ByteArray?): Any {
        return try {
            super.deserialize(topic, headers, data)
        } catch (e: Exception) {
            logger.warn("Unable to deserialize data into class {}:\n{}",
                errorValue.javaClass.simpleName, data?.let { String(it) })
            errorValue
        }
    }

    class ItemJsonSerializer : SafeJsonDeserializer() {
        override val errorValue =
            TezosItemSafeEventDto(TezosItemSafeEventDto.Type.SERIALIZATION_FAILED, null, null, null)
    }

    class OwnershipJsonSerializer : SafeJsonDeserializer() {
        override val errorValue =
            TezosOwnershipSafeEventDto(TezosOwnershipSafeEventDto.Type.SERIALIZATION_FAILED, null, null, null)
    }

    class OrderJsonSerializer : SafeJsonDeserializer() {
        override val errorValue =
            TezosOrderSafeEventDto(TezosOrderSafeEventDto.Type.SERIALIZATION_FAILED, null, null, null)
    }

    class ActivityJsonSerializer : SafeJsonDeserializer() {
        override val errorValue = TezosActivitySafeDto(null, null, null, null)
    }

}