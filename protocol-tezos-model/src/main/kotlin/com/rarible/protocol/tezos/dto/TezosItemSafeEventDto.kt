package com.rarible.protocol.tezos.dto

data class TezosItemSafeEventDto(
    val type: Type,
    val eventId: String?,
    val itemId: String?,
    val item: NftItemDto?
) {

    enum class Type {
        UPDATE,
        DELETE,
        SERIALIZATION_FAILED
    }
}
