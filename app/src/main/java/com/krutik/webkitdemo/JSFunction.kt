package com.krutik.webkitdemo

import android.util.Log
import kotlin.reflect.KClass

open class JSFunctionParent(val innerBridge: InnerBridge, val functionBinding: Long) {

    private var closed: Boolean = false

    protected fun checkClosed() = synchronized(this) {
        if (closed) error("Function is already closed!")
    }

    fun close() {
        synchronized(this) {
            checkClosed()
            closed = true
        }
        innerBridge.removeFunction(this)
    }

    fun finalize() {
        if (!closed) {
            Log.w("JSFunction", "There is no more reference to this function but the close function is not called!")
            innerBridge.removeFunction(this)
        }
    }
}

class JSFunction(InnerBridge: InnerBridge, functionBinding: Long) : JSFunctionParent(InnerBridge, functionBinding) {
    fun call() {
        checkClosed()
        innerBridge.callJSFunction(this)
    }
}

class JSFunctionWithArg<A>(InnerBridge: InnerBridge, functionBinding: Long) : JSFunctionParent(InnerBridge, functionBinding) {
    fun call(arg: A) {
        checkClosed()
        innerBridge.callJSFunction(this, arg)
    }
}

class JSFunctionWithPromise<R>(InnerBridge: InnerBridge, functionBinding: Long, val kClass: KClass<*>) : JSFunctionParent(InnerBridge, functionBinding) {
    fun call(): CallResolver<R> {
        checkClosed()
        return innerBridge.callJSFunctionWithPromise(this)
    }
}

class JSFunctionWithPromiseAndArg<A, R>(InnerBridge: InnerBridge, functionBinding: Long, val kClass: KClass<*>) : JSFunctionParent(InnerBridge, functionBinding) {
    fun call(arg: A): CallResolver<R> {
        checkClosed()
        return innerBridge.callJSFunctionWithPromise(this, arg)
    }
}