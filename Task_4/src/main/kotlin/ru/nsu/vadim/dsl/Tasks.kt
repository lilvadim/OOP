package ru.nsu.vadim.dsl

import ru.nsu.vadim.Configuration
import ru.nsu.vadim.Task
import ru.nsu.vadim.Tasks
import java.time.LocalDate

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