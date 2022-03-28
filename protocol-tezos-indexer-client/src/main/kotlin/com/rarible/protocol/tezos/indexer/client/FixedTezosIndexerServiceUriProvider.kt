package com.rarible.protocol.tezos.indexer.client

import java.net.URI

class FixedTezosIndexerServiceUriProvider(private val fixedURI: URI) : TezosIndexerServiceUriProvider {

    override fun getUri(): URI {
        return fixedURI
    }
}
