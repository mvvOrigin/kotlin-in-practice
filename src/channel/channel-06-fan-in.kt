package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*
 * Fan-in
 *
 * Multiple coroutines may send to the same channel
 *
 * https://kotlinlang.org/docs/reference/coroutines/channels.html#fan-in
 */
fun main() = runBlocking {
    val strings = Channel<String>()

    sendString("foo", strings)
    sendString("BAR!", strings)

    consumeStrings(strings)

    delay(1200)
    println("Done!")
    coroutineContext.cancelChildren()
}

private fun CoroutineScope.sendString(string: String, channel: SendChannel<String>) = launch {
    while (true) {
        delay(250)
        channel.send(string)
    }
}

private fun CoroutineScope.consumeStrings(channel: ReceiveChannel<String>) = launch {
    for (s in channel) println(s)
}
