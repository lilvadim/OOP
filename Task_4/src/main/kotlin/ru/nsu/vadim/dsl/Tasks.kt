package ru.nsu.vadim.dsl

import java.time.LocalDate

@ConfigMarker
class Tasks(private val tasks: MutableList<Task> = mutableListOf()) : MutableList<Task> by tasks {
    fun task(id: String, deadline: LocalDate, init: Task.() -> Unit = {}) {
        tasks += Task(id, deadline).apply(init)
    }
}