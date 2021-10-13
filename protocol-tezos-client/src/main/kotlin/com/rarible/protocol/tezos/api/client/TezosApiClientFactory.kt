package com.rarible.protocol.tezos.api.client

import com.rarible.protocol.tezos.api.ApiClient
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer

open class TezosApiClientFactory(
    private val uriProvider: TezosApiServiceUriProvider,
    private val webClientCustomizer: WebClientCustomizer = NoopWebClientCustomizer()
) {

    fun createNftCollectionApiClient(): NftCollectionControllerApi {
        return NftCollectionControllerApi(createApiClient())
    }

    fun createNftItemApiClient(): NftItemControllerApi {
        return NftItemControllerApi(createApiClient())
    }

    fun createNftOwnershipApiClient(): NftOwnershipControllerApi {
        return NftOwnershipControllerApi(createApiClient())
    }

    fun createNftActivityApiClient(): NftActivityControllerApi {
        return NftActivityControllerApi(createApiClient())
    }

    fun createOrderApiClient(): OrderControllerApi {
        return OrderControllerApi(createApiClient())
    }

    fun createOrderActivityApiClient(): OrderActivityControllerApi {
        return OrderActivityControllerApi(createApiClient())
    }

    fun createOrderAggregationApiClient(): OrderAggregationControllerApi {
        return OrderAggregationControllerApi(createApiClient())
    }

    private fun createApiClient(): ApiClient {
        return ApiClient(webClientCustomizer)
            .setBasePath(uriProvider.getUri().toASCIIString())
    }

}

