package ru.nsu.vadim.dsl

import ru.nsu.vadim.model.*
import java.time.format.DateTimeFormatter

@ConfigMarker
class Configuration {
    var dateTimePattern: String = DEFAULT_PATTERN
        set(value) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(value)
        }

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
    }

    lateinit var group: Group
    val tasks: Tasks = Tasks()
    val lessons: Lessons = Lessons()

    var taskFolderPattern: Task.() -> String = DEFAULT_TASK_FOLDER_PATTERN
    var studentRepoFolderPattern: Student.() -> String = DEFAULT_STUDENT_REPO_PATTERN

    fun group(id: String, init: Group.() -> Unit) {
        group = Group(id).apply(init)
    }

    fun lessons(init: Lessons.() -> Unit) = lessons.init()

    fun tasks(init: Tasks.() -> Unit) = tasks.init()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Configuration

        if (group != other.group) return false
        if (tasks != other.tasks) return false
        if (lessons != other.lessons) return false

        return true
    }

    override fun hashCode(): Int {
        var result = group.hashCode()
        result = 31 * result + tasks.hashCode()
        result = 31 * result + lessons.hashCode()
        return result
    }

    companion object {
        const val DEFAULT_PATTERN = "dd.MM.yyyy"
        val DEFAULT_STUDENT_REPO_PATTERN: Student.() -> String = { name }
        val DEFAULT_TASK_FOLDER_PATTERN: Task.() -> String = { "Task_${id.replace(".", "_")}" }

        var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN)
            private set
    }
}
