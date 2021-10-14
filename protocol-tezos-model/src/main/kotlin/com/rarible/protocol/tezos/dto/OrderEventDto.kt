package com.rarible.protocol.tezos.dto

class OrderEventDto(
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