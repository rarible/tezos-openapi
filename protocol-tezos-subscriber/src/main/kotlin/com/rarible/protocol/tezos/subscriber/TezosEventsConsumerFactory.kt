package com.rarible.protocol.tezos.subscriber

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.protocol.tezos.dto.TezosActivitySafeDto
import com.rarible.protocol.tezos.dto.TezosCollectionSafeEventDto
import com.rarible.protocol.tezos.dto.TezosEventTopicProvider
import com.rarible.protocol.tezos.dto.TezosItemSafeEventDto
import com.rarible.protocol.tezos.dto.TezosOrderSafeEventDto
import com.rarible.protocol.tezos.dto.TezosOwnershipSafeEventDto
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

    fun createItemConsumer(consumerGroup: String): RaribleKafkaConsumer<TezosItemSafeEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-item-consumer",
            valueDeserializerClass = SafeJsonDeserializer.ItemJsonSerializer::class.java,
            valueClass = TezosItemSafeEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ITEM,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createCollectionConsumer(consumerGroup: String): RaribleKafkaConsumer<TezosCollectionSafeEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-collection-consumer",
            valueDeserializerClass = SafeJsonDeserializer.CollectionJsonSerializer::class.java,
            valueClass = TezosCollectionSafeEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ITEM,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createOwnershipConsumer(consumerGroup: String): RaribleKafkaConsumer<TezosOwnershipSafeEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-ownership-consumer",
            valueDeserializerClass = SafeJsonDeserializer.OwnershipJsonSerializer::class.java,
            valueClass = TezosOwnershipSafeEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.OWNERSHIP,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createOrderConsumer(consumerGroup: String): RaribleKafkaConsumer<TezosOrderSafeEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-order-consumer",
            valueDeserializerClass = SafeJsonDeserializer.OrderJsonSerializer::class.java,
            valueClass = TezosOrderSafeEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ORDER,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createActivityConsumer(consumerGroup: String): RaribleKafkaConsumer<TezosActivitySafeDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-activity-consumer",
            valueDeserializerClass = SafeJsonDeserializer.ActivityJsonSerializer::class.java,
            valueClass = TezosActivitySafeDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = TezosEventTopicProvider.ACTIVITY,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

}