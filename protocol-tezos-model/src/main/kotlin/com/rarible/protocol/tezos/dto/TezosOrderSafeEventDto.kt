package com.rarible.protocol.tezos.dto

class TezosOrderSafeEventDto(
    val type: Type,
    val eventId: String?,
    val orderId: String?,
    val order: OrderDto?
) {

    enum class Type {
        UPDATE,
        SERIALIZATION_FAILED
    }

}