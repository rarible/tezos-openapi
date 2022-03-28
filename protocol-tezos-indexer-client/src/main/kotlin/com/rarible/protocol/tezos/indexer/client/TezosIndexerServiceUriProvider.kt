package com.rarible.protocol.tezos.indexer.client

import java.net.URI

interface TezosIndexerServiceUriProvider {

    fun getUri(): URI

}
