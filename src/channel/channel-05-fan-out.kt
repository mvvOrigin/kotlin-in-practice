package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*
 * Fan-out
 *
 * Multiple coroutines may receive from the same channel
 * distributing work between themselves
 *
 * https://kotlinlang.org/docs/reference/coroutines/channels.html#fan-out
 */

fun main() = runBlocking {
    val orders = produceOrders()

    handleOrder(1, orders)
    handleOrder(2, orders)

    println("Done")
}

private fun CoroutineScope.produceOrders(): ReceiveChannel<Int> = produce {
    for (o in 1..10) {
        delay(100)
        send(o)
    }
}

private fun CoroutineScope.handleOrder(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (x in channel) println("Barista#$id received $x")
}