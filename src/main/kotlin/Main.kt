package com

import java.net.ServerSocket
import java.util.concurrent.Executors

fun main() {
    println("start Kotlin WAS!")

    val server = ServerSocket(8080)
    val threadPool = Executors.newFixedThreadPool(10)

    while (true) {
        val client = server.accept()
        threadPool.submit {
            client.use {
                val input = it.getInputStream().bufferedReader()
                val output = it.getOutputStream().bufferedWriter()

                val requestLine = input.readLine()
                println("Request: $requestLine")

                val response = """
                    HTTP/1.1 200 OK
                    Content-Type: text/plain
                    Content-Length: 30

                    Hello from Kotlin WAS
                """.trimIndent()

                output.write(response)
                output.flush()
            }
        }
    }


}
