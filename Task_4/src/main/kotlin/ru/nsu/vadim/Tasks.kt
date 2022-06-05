package ru.nsu.vadim

import java.time.LocalDate

typealias Tasks = MutableList<Task>

open class TasksBuilder(private val tasks: Tasks = mutableListOf()) {
    fun task(id: String, deadline: LocalDate) {
        tasks += (Task(id, deadline))
    }

    fun task(id: String, deadline: String) = task(id, LocalDate.parse(deadline))

    fun build() = tasks
}

fun Configuration.tasks(init: TasksBuilder.() -> Unit) {
    val tasksBuilder = TasksBuilder()
    tasksBuilder.init()
    tasks.addAll(tasksBuilder.build())
}