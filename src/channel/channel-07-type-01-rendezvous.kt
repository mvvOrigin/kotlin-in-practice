package channel

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 * Channel types: UNLIMITED, BUFFERED, CONFLATED, RENDEZVOUS
 *
 * 'receive' call is always suspended for all types
 * 'send' call can behave suspended or not
 *
 * RENDEZVOUS is default channel type
 * it prepares the value & suspends `send` call until at least one consumer comes
 * 'rendezvous means - send and receive should "meet on time"'
 *
 * 'send' in suspended
 *
 * https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/08_Channels
 */

fun main() = runBlocking {
    val channel = Channel<Int>()

    // produce items with delay
    launch {
        for (i in 1..100) {
            delay(250)
            channel.send(i)
            println("sent: $i")
        }
    }

    // make a delay to have a look of produced items before consumer comes
    delay(1000)

    // consume some items
    repeat(5) {
        println("received: ${channel.receive()}")
    }

    // make a delay to have a look of produced items after consumer leaves
    delay(2000)

    // finish
    print("Done")
    coroutineContext.cancelChildren()
}

