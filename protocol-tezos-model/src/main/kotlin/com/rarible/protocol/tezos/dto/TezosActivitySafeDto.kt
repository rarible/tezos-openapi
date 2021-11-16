package com.rarible.protocol.tezos.dto

import java.time.Instant

data class TezosActivitySafeDto(
    val id: String?,
    val date: Instant?,
    val source: String?,
    val type: ActivityTypeDto?
)
