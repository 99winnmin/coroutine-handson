package net.chandol.sample.coroutine.api.example

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.chandol.sample.coroutine.api.util.log

// 동기를 비동기로 처리해보자
fun main() = runBlocking {
    val num1000 = async { calcNum(1000) } // async 를 달아야 비동기 처리됌
    val num2000 = async { calcNum(2000) }

    val calc = num1000.await() + num2000.await()
    log("$calc")
}

// suspend 가 항상 좋은 것은 아님, 일반적으로 부하가 더 걸림
private suspend fun calcNum(num: Int): Int { // suspend 의의 : 이건 코루틴 함수야!
    log("input : $num 계산 시작")
    delay(1000) // suspend 안붙이면 코루틴이 아님
    return num
}
