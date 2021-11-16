package com.rarible.protocol.tezos.dto

class TezosOwnershipSafeEventDto(
    val type: Type,
    val eventId: String?,
    val ownershipId: String?,
    val ownership: NftOwnershipDto?
) {

    enum class Type {
        UPDATE,
        DELETE,
        SERIALIZATION_FAILED
    }

}