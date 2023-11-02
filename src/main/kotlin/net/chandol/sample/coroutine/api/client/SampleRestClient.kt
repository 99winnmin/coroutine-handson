package net.chandol.sample.coroutine.api.client

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration


@Component
class SampleRestClient {
    private val client: WebClient = WebClient.create("https://httpbin.org")
    fun sampleGet(param: String): Mono<String> {
        return client.get()
            .uri { uriBuilder ->
                uriBuilder.path("/get")
                    .queryParam("param", param)
                    .build()
            }
            .exchangeToMono {
                it.bodyToMono(String::class.java)
            }
    }

    // Mono 를 최대한 숨김 ( 코루틴을 사용한다고 하면 )
    suspend fun sampleGet2(param: String): String {
        return client.get()
            .uri { uriBuilder ->
                uriBuilder.path("/get")
                    .queryParam("param", param)
                    .build()
            }
            .exchangeToMono {
                it.bodyToMono(String::class.java)
            }
            .awaitSingle()
    }
}
