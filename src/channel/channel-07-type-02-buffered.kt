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
 * BUFFERED channel has a buffer where produced items will be collected while consumer comes
 *
 * 'send' is suspended
 *
 * https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/08_Channels
 */

fun main() = runBlocking {
    val channel = Channel<Int>(capacity = 2)

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
