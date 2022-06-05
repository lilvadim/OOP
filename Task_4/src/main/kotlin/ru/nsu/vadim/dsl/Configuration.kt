package ru.nsu.vadim.dsl

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ConfigMarker
class Configuration {

    var dateTimePattern: String = DEFAULT_PATTERN
        set(value) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(value)
        }

    companion object Settings {
        var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN)
    }

    lateinit var group: Group
    val tasks: Tasks = Tasks()

    fun group(id: String, init: Group.() -> Unit) {
        group = Group(id).apply(init)
    }

    fun tasks(init: Tasks.() -> Unit) = tasks.init()
}

const val DEFAULT_PATTERN = "dd.MM.yyyy"

fun Tasks.task(id: String, deadline: String, init: Task.() -> Unit = {}) =
    task(id, LocalDate.parse(deadline, Configuration.dateTimeFormatter), init)

fun Lessons.lesson(date: String) = lesson(LocalDate.parse(date, Configuration.dateTimeFormatter))

fun Grades.grade(name: String, date: String) =
    grade(name, LocalDate.parse(date, Configuration.dateTimeFormatter))

fun config(init: Configuration.() -> Unit): Configuration = Configuration().apply(init)

