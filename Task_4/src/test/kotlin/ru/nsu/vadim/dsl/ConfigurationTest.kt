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
        val config = config {
            dateTimePattern = "dd.MM.yyyy"
            totalLessonsCount = 8
            taskFolderName = { "Task-${id}" }

            tasks {
                task(id = "1.2", deadline = LocalDate.now()) {
                    description("codeName") {
                        text = """
                        gimme snake
                    """.trimIndent()
                        score = 1
                    }
                }
                task(id = "3.3.1", "12.05.2022")
            }

            group(id = "20214") {
                student(name = "Vadim Mostovoy") {
                    repositoryUrl = ""
                    username = "lilvadim"

                    grades {
                        grade("Excellent", "22.05.2022")
                    }

                    lessons {
                        lesson("12.03.2022")
                        lesson("19.03.2022")
                        lesson("26.03.2022")
                    }
                }
            }
        }

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