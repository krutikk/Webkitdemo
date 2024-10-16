package com.krutik.webkitdemo.sample

import com.krutik.webkitdemo.CallType
import com.krutik.webkitdemo.JSFunctionWithArg
import com.krutik.webkitdemo.JSFunctionWithPromise
import com.krutik.webkitdemo.JSFunctionWithPromiseAndArg
import com.krutik.webkitdemo.NativeCall
import com.krutik.webkitdemo.CallResolver

interface AndroidInterface {

    @NativeCall(CallType.FULL_SYNC)
    fun helloFullSync(name: String): String

    @NativeCall(CallType.WEB_CALL)
    fun helloWebPromise(name: String): String

    @NativeCall(CallType.FULL_PROMISE)
    fun helloFullPromise(name: String): CallResolver<String>

    @NativeCall(CallType.FULL_PROMISE)
    fun registerFunction(function: JSFunctionWithArg<Int>): CallResolver<Unit>

    @NativeCall(CallType.FULL_PROMISE)
    fun registerFunctionWithPromise(function: JSFunctionWithPromise<String>): CallResolver<Unit>

    @NativeCall(CallType.FULL_PROMISE)
    fun registerFunctionWithPromiseAndArg(function: JSFunctionWithPromiseAndArg<Add, String>): CallResolver<Unit>
}

data class Add(val a: Int, val b: Int)