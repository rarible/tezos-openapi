package com.rarible.protocol.tezos.api.client.autoconfigure

import com.rarible.core.application.ApplicationEnvironmentInfo
import com.rarible.protocol.tezos.api.client.TezosApiClientFactory
import com.rarible.protocol.tezos.api.client.TezosApiServiceUriProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
@Import(TezosApiClientAutoConfigurationIt.Configuration::class)
class TezosApiClientAutoConfigurationIt {

    @Autowired
    private lateinit var tezosApiServiceUriProvider: TezosApiServiceUriProvider

    @Autowired
    private lateinit var tezosApiClientFactory: TezosApiClientFactory

    @Autowired
    @Qualifier(TEZOS_WEB_CLIENT_CUSTOMIZER)
    private lateinit var webClientCustomizer: WebClientCustomizer

    @Test
    fun `test default clients initialized`() {
        assertThat(tezosApiServiceUriProvider).isNotNull
        assertThat(tezosApiClientFactory).isNotNull

        every { webClientCustomizer.customize(any()) } returns Unit

        tezosApiClientFactory.createOrderApiClient()

        verify { webClientCustomizer.customize(any()) }
    }

    @Test
    fun `test default client uri`() {
        val uri = tezosApiServiceUriProvider.getUri()
        assertThat(uri.toString()).isEqualTo("https://localhost:8080/tezos/test")
    }

    @TestConfiguration
    internal class Configuration {

        @Bean
        @Qualifier(TEZOS_WEB_CLIENT_CUSTOMIZER)
        fun webClientCustomizer(): WebClientCustomizer {
            return mockk()
        }

        @Bean
        fun applicationEnvironmentInfo(): ApplicationEnvironmentInfo {
            return ApplicationEnvironmentInfo("test", "test.com")
        }
    }
}
