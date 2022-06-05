package ru.nsu.vadim

import java.time.LocalDate

fun main(args: Array<String>) {
    Configuration {
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