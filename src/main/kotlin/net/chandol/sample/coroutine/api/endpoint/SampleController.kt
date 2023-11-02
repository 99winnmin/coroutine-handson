package net.chandol.sample.coroutine.api.endpoint

import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import net.chandol.sample.coroutine.api.client.SampleRestClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

// request -> netty(spring webflux -> coroutine) -> response
// https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#coroutines
// https://spring.io/blog/2019/04/12/going-reactive-with-spring-Coroutines-and-kotlin-flow
@RestController
class SampleController(
    val sampleRestClient: SampleRestClient
) {
    //webclient 호출 이후 결괏값 응답하는 coroutine handler
    @GetMapping("/hello")
    fun hello(@RequestParam param: String): Mono<ResponseEntity<String>> {
        return Mono.just(param)
            .doOnNext { log.info { "요청 받음... $it" } }
            .flatMap { sampleRestClient.sampleGet(param) }
            .map { ResponseEntity.ok(it) }
            .doOnNext { log.info { "요청 완료!... ${it.body}" } }
    }

    // 위에꺼보다 가독성이 훨씬 좋음
    @GetMapping("/hello2")
    suspend fun hello2(@RequestParam param: String): ResponseEntity<String> {
        log.info { "요청 받음... $param" }
        // awaitSingle()을 통해 coroutine 으로 처리하고 비동기로 응답한다.
        val response = sampleRestClient.sampleGet2(param)
//        val response = sampleRestClient.sampleGet(param).block() // 에러남
        log.info { "요청 완료!... ${response}" }

        return ResponseEntity.ok(response)
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
