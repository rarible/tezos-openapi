package com.rarible.protocol.tezos.api.client

import java.net.URI

class FixedTezosApiServiceUriProvider(private val fixedURI: URI) : TezosApiServiceUriProvider {

    override fun getUri(): URI {
        return fixedURI
    }
}