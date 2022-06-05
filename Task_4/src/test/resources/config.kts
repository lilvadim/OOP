import ru.nsu.vadim.dsl.*
import ru.nsu.vadim.dsl.Configuration.Settings.taskFolderName
import java.time.LocalDate

config {
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