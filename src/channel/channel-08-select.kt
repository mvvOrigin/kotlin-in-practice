package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    val fizz = fizz()
    val buzz = buzz()

    // hence 'receive' is suspending function we can consume events from one or another channels
    repeat(7) { println(fizz.receive()) }
    repeat(7) { println(buzz.receive()) }

    // with 'select' extension we can consume from both simultaneously
//    repeat(7) { println(selectFor(fizz, buzz)) }

    coroutineContext.cancelChildren()
}

suspend fun selectFor(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>): String =
    select {
        // keep here some shared instance

        fizz.onReceive {
            /*
             * if you receive from 'fizz' do this
             */
            "in select from fizz: $it"
        }
        buzz.onReceive {
            /*
             * if you receive from 'buzz' do this
             */
            "in select from buzz: $it"
        }
    }

// sends "Fizz" every 300 ms
private fun CoroutineScope.fizz() = produce {
    var i = 0
    while (true) {
        delay(300)
        send("Fizz #${i++}")
    }
}

// sends "Buzz!" every 500 ms
private fun CoroutineScope.buzz() = produce {
    var i = 0
    while (true) {
        delay(500)
        send("Buzz #${i++}")
    }
}