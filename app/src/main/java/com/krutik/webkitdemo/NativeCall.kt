package com.krutik.webkitdemo

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NativeCall(val value: CallType = CallType.FULL_PROMISE)

enum class CallType{FULL_SYNC, WEB_CALL, FULL_PROMISE}