package ru.nsu.vadim.dsl

import ru.nsu.vadim.model.Group
import ru.nsu.vadim.model.Task
import ru.nsu.vadim.model.Tasks
import java.time.format.DateTimeFormatter

@ConfigMarker
class Configuration {
    var totalLessonsCount: Int = 16

    var dateTimePattern: String = DEFAULT_PATTERN
        set(value) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(value)
        }

    fun Task.folderName(): String = taskFolderName()

    companion object Settings {
        const val DEFAULT_PATTERN = "dd.MM.yyyy"

        var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN)
            private set

        var taskFolderName: Task.() -> String = { "Task_{${id.replace(".", "_")}}" }
    }

    lateinit var group: Group
    val tasks: Tasks = Tasks()

    fun group(id: String, init: Group.() -> Unit) {
        group = Group(id).apply(init)
    }

    fun tasks(init: Tasks.() -> Unit) = tasks.init()
}
