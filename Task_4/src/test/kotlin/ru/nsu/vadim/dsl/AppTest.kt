package ru.nsu.vadim.dsl

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun test() {
        val parsed = App().apply {
            main(arrayOf("src/test/resources/config.kts"))
        }.configuration

        assertEquals(testConfiguration(), parsed)
    }
}