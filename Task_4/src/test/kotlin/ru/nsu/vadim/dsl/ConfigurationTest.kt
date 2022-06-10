package ru.nsu.vadim.dsl

import org.junit.jupiter.api.Test
import ru.nsu.vadim.model.Grade
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

class ConfigurationTest {
    @Test
    fun `test if DSL is working fine`() {

        val config = SAMPLE_CONFIG

        assertEquals(1, config.tasks.size)
        assertEquals(1, config.groups.size)
        assertEquals("3.3.1", config.tasks[0].id)
        assertEquals(
            listOf(
                Grade(
                    "Excellent",
                    LocalDate.parse(
                        "22.05.2022",
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    )
                )
            ),
            config.groups[0][0].grades
        )

        assertEquals(3, config.lessons.size)
        assertEquals("Task_3_3_1",
            with(config) {
                tasks[0].taskFolderPattern()
            }
        )
    }
}