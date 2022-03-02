package com.thanh_nguyen.test_count_down

import android.icu.util.ChineseCalendar
import com.thanh_nguyen.test_count_down.utils.CMN
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `thanh ne`(){
        val fruits = listOf("cherry", "blueberry", "citrus", "apple", "apricot", "banana", "coconut")

        val evenFruits = fruits.groupingBy { it.first() }
            .fold(
                { key, value ->
                    value
                },
                { _, accumulator, element ->
                    accumulator.also {
                        println("dât ne: ${it}")
                    }
                }
            )

//        val sorted = evenFruits.values.sortedBy { it.first }
        println(evenFruits.values.toString()) // [(a, []), (b, [banana]), (c, [cherry, citrus])]
    }
}