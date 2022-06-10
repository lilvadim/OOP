package ru.nsu.vadim.dsl

import org.junit.jupiter.api.Test
import ru.nsu.vadim.app.parse
import java.io.File
import kotlin.test.assertEquals

class ParseTest {
    @Test
    fun `parse using ScriptEngine API`() {

        val parsed = parse(File("src/test/resources/sampleConfig.kts").readText())

        assertEquals(SAMPLE_CONFIG, parsed)
    }
}