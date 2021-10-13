package com.rarible.protocol.tezos.api.client

import java.net.URI

interface TezosApiServiceUriProvider {

    fun getUri(): URI

}

