import ru.nsu.vadim.dsl.*
import java.io.File

config {

    dateTimePattern = "dd.MM.yyyy"
    groupFolderPattern = { "g$id" }
    studentRepoFolderPattern = { username }
    reposSubDir = "git"
    reportFile = File("/Users/vadim/report.html")
    withDocs = true

    tasks {
        deadlineInclusive = true
//        task(id = "1.1.1", deadline = ofYearDay(2022, 21)) {
//            description("Snake") {
//                text = """
//                        gimme snake
//                    """.trimIndent()
//                score = 1
//            }
//        }
        task(id = "3.3.1", "10.06.2022") {
            description("Snake Game")
        }
    }

    lessonsForGroups("20214") {
        lesson("02.06.2022")
        lesson("19.03.2022")
        lesson("26.03.2022")
        lesson("03.06.2022")
    }

    lessonsForGroups("20215") {
        lesson("30.06.2022")
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
    group(id = "20215") {
        student(name = "Vadim Мостовой 1") {
            repositoryUrl = "https://github.com/lilvadim/OOP"
            username = "lilvadim"

            grades {
                grade("Excellent", "22.05.2022")
            }
        }
    }
}