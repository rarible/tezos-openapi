package com.rarible.protocol.tezos.api.client

import java.net.URI

interface TezosIndexerServiceUriProvider {

    fun getUri(): URI

}
