package com.rarible.protocol.tezos.indexer.api

import java.io.InputStream

object TezosOpenapiReader {

    fun getOpenapi(): InputStream =
        TezosOpenapiReader::class.java.getResourceAsStream("/openapi.yaml")!!

}
