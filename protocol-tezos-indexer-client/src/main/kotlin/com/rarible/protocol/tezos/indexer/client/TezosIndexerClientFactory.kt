package com.rarible.protocol.tezos.indexer.client

import com.rarible.protocol.tezos.indexer.ApiClient
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer

open class TezosIndexerClientFactory(
    private val uriIndexerProvider: TezosIndexerServiceUriProvider,
    private val webClientCustomizer: WebClientCustomizer = NoopWebClientCustomizer()
) {

    fun createOrderApiClient(): OrderControllerApi {
        return OrderControllerApi(createIndexerApiClient())
    }

    fun createNftItemApiClient(): NftItemControllerApi {
        return NftItemControllerApi(createIndexerApiClient())
    }

    fun createNftCollectionApiClient(): NftCollectionControllerApi {
        return NftCollectionControllerApi(createIndexerApiClient())
    }

    private fun createIndexerApiClient(): ApiClient {
        val jacksonMapper = ApiClient.createDefaultObjectMapper()
            .registerModule(NumberJacksonModule)

        return ApiClient(webClientCustomizer, jacksonMapper)
            .setBasePath(uriIndexerProvider.getUri().toASCIIString())
    }

}
