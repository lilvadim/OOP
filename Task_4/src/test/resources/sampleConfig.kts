import ru.nsu.vadim.dsl.*

config {
    dateTimePattern = "dd.MM.yyyy"
//    taskFolderPattern = { "Task-$id" }

    tasks {
//        task(id = "1.1.1", deadline = ofYearDay(2022, 21)) {
//            description("Snake") {
//                text = """
//                        gimme snake
//                    """.trimIndent()
//                score = 1
//            }
//        }
        task(id = "3.3.1", "10.06.2022")
    }

    lessons {
        lesson("02.06.2022")
        lesson("19.03.2022")
        lesson("26.03.2022")
    }

    group(id = "20214") {
        student(name = "Vadim Mostovoy") {
            repositoryUrl = "https://github.com/lilvadim/OOP"
            username = "lilvadim"

            grades {
                grade("Excellent", "22.05.2022")
            }
        }
    }
}