package ru.nsu.vadim.dsl

import java.time.format.DateTimeFormatter

class Configuration {

    var dateTimePattern: String = "dd.MM.yyyy"
        set(value) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(value)
        }

    private var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)

    lateinit var group: Group
    val tasks: Tasks = Tasks(dateTimeFormatter = dateTimeFormatter)
    val grades: Grades = Grades(dateTimeFormatter = dateTimeFormatter)
    val lessons: Lessons = Lessons(dateTimeFormatter = dateTimeFormatter)

    fun group(id: String, init: Group.() -> Unit) {
        group = Group(id).apply(init)
    }

    fun tasks(init: Tasks.() -> Unit) = tasks.init()
    fun grades(init: Grades.() -> Unit) = grades.init()
    fun lessons(init: Lessons.() -> Unit) = lessons.init()
}

fun config(init: Configuration.() -> Unit): Configuration = Configuration().apply(init)

