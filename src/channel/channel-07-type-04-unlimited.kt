package channel

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 * Channel types: UNLIMITED, BUFFERED, CONFLATED, RENDEZVOUS
 *
 * 'receive' call is always suspended for all types
 * 'send' call can behave suspended or not
 *
 * UNLIMITED channel produce items no matter consumer subscribe or not
 *
 * 'send' is not suspended
 *
 * https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/08_Channels
 */

fun main() = runBlocking {
    val channel = Channel<Int>(Channel.UNLIMITED)

    // produce items continuously
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
