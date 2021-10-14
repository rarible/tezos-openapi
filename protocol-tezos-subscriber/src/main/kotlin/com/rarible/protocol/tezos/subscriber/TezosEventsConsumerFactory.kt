package com.rarible.protocol.tezos.subscriber

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.protocol.tezos.dto.ItemEventDto
import com.rarible.protocol.tezos.dto.OrderEventDto
import com.rarible.protocol.tezos.dto.OwnershipEventDto
import com.rarible.protocol.tezos.dto.TezosEventTopicProvider
import java.util.*

class TezosEventsConsumerFactory(
    private val brokerReplicaSet: String,
    host: String,
    environment: String,
    username: String?,
    password: String?
) {

    private val clientIdPrefix = "$environment.$host.${UUID.randomUUID()}"
    private val properties = if (username != null && password != null) mapOf(
        "security.protocol" to "SASL_PLAINTEXT",
        "sasl.mechanism" to "PLAIN",
        "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"$username\" password=\"$password\";"
    ) else emptyMap()

    fun createItemConsumer(consumerGroup: String): RaribleKafkaConsumer<ItemEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-item-consumer",
            valueDeserializerClass = SafeJsonDeserializer.ItemJsonSerializer::class.java,
            valueClass = ItemEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ITEM,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createOwnershipConsumer(consumerGroup: String): RaribleKafkaConsumer<OwnershipEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-ownership-consumer",
            valueDeserializerClass = SafeJsonDeserializer.OwnershipJsonSerializer::class.java,
            valueClass = OwnershipEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.OWNERSHIP,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createOrderConsumer(consumerGroup: String): RaribleKafkaConsumer<OrderEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-order-consumer",
            valueDeserializerClass = SafeJsonDeserializer.OrderJsonSerializer::class.java,
            valueClass = OrderEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ORDER,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

}