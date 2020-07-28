package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*
 * Pipelines
 *
 * A pipeline is a pattern where one coroutine is producing stream of values (possibly infinite)
 * And another coroutine is consuming that stream, doing some processing, and producing some other results
 * And another coroutine is consuming that stream fo final results
 *
 * https://kotlinlang.org/docs/reference/coroutines/channels.html#pipelines
 */

fun main() = runBlocking {
    val numbers = produceNumbers()
    val squares = produceSquares(numbers)

    for (s in squares) {
        println("received: $s")
    }

    println("Done")
}

private fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
    for (o in 1..10) send(o)
}

private fun CoroutineScope.produceSquares(orders: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (o in orders) send(o * o)
}

