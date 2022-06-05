package ru.nsu.vadim

import ru.nsu.vadim.dsl.config
import ru.nsu.vadim.dsl.group
import ru.nsu.vadim.dsl.tasks
import java.time.LocalDate

fun main(args: Array<String>) {
    config {
        tasks {
            task(id = "1.2", deadline = LocalDate.now())
            task(id = "3.3.1", LocalDate.now())
        }

        group(id = "20214") {
            student(name = "Vadim Mostovoy") {
                repositoryUrl = ""
                username = "lilvadim"
            }
        }
    }.let { conf ->
        println("> Group")
        conf.group.forEach { println(it) }
        println("> Tasks")
        conf.tasks.forEach { println(it) }
    }
}