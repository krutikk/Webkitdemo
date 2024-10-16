package com.krutik.webkitdemo

import java.util.concurrent.Executors

interface JSInterface {
    val name: String
}

open class DefaultJSInterface(override val name: String) : JSInterface {

    private val executor = Executors.newCachedThreadPool()

    fun <T> doInBackground(block: (CallResolver<T>) -> Unit): CallResolver<T> {
        val callResolver = CallResolver<T>()
        executor.execute { block(callResolver) }
        return callResolver
    }
}