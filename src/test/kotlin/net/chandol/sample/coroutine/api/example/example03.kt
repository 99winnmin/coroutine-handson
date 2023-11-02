package net.chandol.sample.coroutine.api.example

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.chandol.sample.coroutine.api.util.log

// async를 사용해보자
fun main() = runBlocking {
    log("start")
    val deferredNum1: Deferred<Int> = async {
        val num1 = 100
//        delay(1000)
        Thread.sleep(1000) // 스레드를 생성하는건 오래 걸림... + 메인 스레드(모체 스레드) 전체가 Blocking 걸림
        return@async num1/* 가장 마지막 값이 리턴됌. return@async 없어도됌  */
    }

    val deferredNum2: Deferred<Int> = async {
        val num2 = 200
        delay(1000)
        num2/* 가장 마지막 값이 리턴됌. return@async 없어도됌  */
    }

    val calc = deferredNum1.await() + deferredNum2.await()
    log("$calc")
}
