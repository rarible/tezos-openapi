package com.rarible.protocol.tezos.api.client.autoconfigure

import com.rarible.core.application.ApplicationEnvironmentInfo
import com.rarible.protocol.tezos.api.client.CompositeWebClientCustomizer
import com.rarible.protocol.tezos.api.client.DefaultTezosWebClientCustomizer
import com.rarible.protocol.tezos.api.client.FixedTezosApiServiceUriProvider
import com.rarible.protocol.tezos.api.client.NoopWebClientCustomizer
import com.rarible.protocol.tezos.api.client.TezosApiClientFactory
import com.rarible.protocol.tezos.api.client.TezosApiServiceUriProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean
import java.net.URI

const val TEZOS_WEB_CLIENT_CUSTOMIZER = "TEZOS_WEB_CLIENT_CUSTOMIZER"

class TezosApiClientAutoConfiguration(
    private val applicationEnvironmentInfo: ApplicationEnvironmentInfo
) {

    @Autowired(required = false)
    @Qualifier(TEZOS_WEB_CLIENT_CUSTOMIZER)
    private var webClientCustomizer: WebClientCustomizer = NoopWebClientCustomizer()

    @Bean
    @ConditionalOnMissingBean(TezosApiServiceUriProvider::class)
    fun tezosApiServiceUriProvider(): TezosApiServiceUriProvider {
        return FixedTezosApiServiceUriProvider(URI("https://localhost:8080/tezos/${applicationEnvironmentInfo.name}"))
    }

    @Bean
    @ConditionalOnMissingBean(TezosApiClientFactory::class)
    fun tezosApiClientFactory(tezosApiServiceUriProvider: TezosApiServiceUriProvider): TezosApiClientFactory {
        val customizer = CompositeWebClientCustomizer(listOf(DefaultTezosWebClientCustomizer(), webClientCustomizer))
        return TezosApiClientFactory(tezosApiServiceUriProvider, customizer)
    }
}