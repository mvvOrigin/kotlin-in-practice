package channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 * A Channel is conceptually very similar to BlockingQueue
 *
 * https://kotlinlang.org/docs/reference/coroutines/channels.html#channel-basics
 */
fun main() = runBlocking {
    val channel = Channel<Int>()

    launch {
        for (o in 1..10) channel.send(o)
        // ! do not forget to close channel when you're done
        // ! receive is always suspended
        channel.close()
    }

    /* can receive single item ~ queue.pop */
    println("received once: ${channel.receive()}")

    /* can receive all items with simple loop */
    for (o in channel) println("received in loop: $o")

    /* can receive all items with extension */
    channel.consumeEach {
        println("received via extension: $it")
    }
}