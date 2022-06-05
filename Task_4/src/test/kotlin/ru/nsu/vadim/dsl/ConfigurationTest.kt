package ru.nsu.vadim.dsl

import org.junit.jupiter.api.Test
import ru.nsu.vadim.dsl.Configuration.Settings.taskFolderName
import ru.nsu.vadim.model.Grade
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

class ConfigurationTest {
    @Test
    fun test() {
        val config = testConfiguration()

        assertEquals(2, config.tasks.size)
        assertEquals(1, config.group.size)
        assertEquals("3.3.1", config.tasks[1].id)
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
            config.group[0].grades
        )
        assertEquals(3, config.group[0].lessons.size)
        assertEquals("Task-1.2", config.tasks[0].taskFolderName())
    }
}