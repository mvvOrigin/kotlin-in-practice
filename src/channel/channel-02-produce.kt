package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*
 * Produce
 *
 * This is a part of producer-consumer pattern that is often found in concurrent code
 * builder named "produce" makes it easy to do it right on producer side
 * it has implicit 'close' call at the end
 *
 * https://kotlinlang.org/docs/reference/coroutines/channels.html#building-channel-producers
 */

fun main() = runBlocking {
    /*
     * Use extension with implicit close token at the end
     */
    val channel: ReceiveChannel<Int> = produce {
        for (o in 1..10) send(o)
        // implicit close call
    }

    for (i in channel) {
        println("received: $i")
    }

    println("Done!")
}