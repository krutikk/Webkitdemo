package com.krutik.webkitdemo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val a : Int = 128;
    val b : Int = 128;
    @Test
    fun addition_isCorrect() {
        println(a == b); //
        assertEquals(4, 2 + 2)
    }

}