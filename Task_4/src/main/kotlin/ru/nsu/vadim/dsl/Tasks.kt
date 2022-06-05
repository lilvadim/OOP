package ru.nsu.vadim.dsl

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ConfigMarker
class Tasks(
    private val tasks: MutableList<Task> = mutableListOf(),
    val dateTimeFormatter: DateTimeFormatter
) : MutableList<Task> by tasks {

    fun task(id: String, deadline: LocalDate, init: Task.() -> Unit = {}) {
        tasks += Task(id, deadline).apply(init)
    }

    fun task(id: String, deadline: String, init: Task.() -> Unit = {}) =
        task(id, LocalDate.parse(deadline, dateTimeFormatter), init)
}