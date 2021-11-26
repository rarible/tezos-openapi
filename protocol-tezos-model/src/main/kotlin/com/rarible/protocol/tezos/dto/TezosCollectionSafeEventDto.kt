package com.rarible.protocol.tezos.dto

data class TezosCollectionSafeEventDto(
    val type: Type,
    val eventId: String?,
    val collectionId: String?,
    val collection: NftCollectionDto?
) {

    enum class Type {
        UPDATE,
        SERIALIZATION_FAILED
    }
}
