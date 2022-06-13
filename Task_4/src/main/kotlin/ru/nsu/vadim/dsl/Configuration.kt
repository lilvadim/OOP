package ru.nsu.vadim.dsl

import ru.nsu.vadim.model.*
import java.io.File
import java.time.format.DateTimeFormatter

const val DEFAULT_PATTERN = "dd.MM.yyyy"
val DEFAULT_STUDENT_REPO_PATTERN: Student.() -> String = { name }
val DEFAULT_TASK_FOLDER_PATTERN: Task.() -> String = { "Task_${id.replace(".", "_")}" }
val DEFAULT_GROUP_FOLDER_PATTERN: Group.() -> String = { id }
const val DEFAULT_REPOS_SUBDIR = "repositories"

@ConfigMarker
class Configuration {
    var dateTimePattern: String = DEFAULT_PATTERN
        set(value) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(value)
        }

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
    }

    val groups: MutableList<Group> = mutableListOf()
    val tasks: Tasks = Tasks()

    private val lessonsPerGroupHolder: MutableMap<String, Lessons> = mutableMapOf()
    val lessonsPerGroup: MutableMap<Group, Lessons> by lazy {
        lessonsPerGroupHolder.mapKeys { (key, _) ->
            groups.find { it.id == key }!!
        }.toMutableMap()
    }

    var withDocs = false

    var reposSubDir = DEFAULT_REPOS_SUBDIR
    var taskFolderPattern: Task.() -> String = DEFAULT_TASK_FOLDER_PATTERN
    var studentRepoFolderPattern: Student.() -> String = DEFAULT_STUDENT_REPO_PATTERN
    var groupFolderPattern: Group.() -> String = DEFAULT_GROUP_FOLDER_PATTERN
    var reportFile: File? = null

    fun group(id: String, init: Group.() -> Unit) {
        groups += Group(id).apply(init)
    }

    fun lessonsForGroups(vararg groupIds: String, init: Lessons.() -> Unit) {
        val lessons = Lessons().apply(init)
        for (gid in groupIds) {
            if (lessonsPerGroupHolder[gid] == null) {
                lessonsPerGroupHolder += gid to lessons
            } else {
                lessonsPerGroupHolder[gid]!! += lessons
            }
        }
    }

    fun tasks(init: Tasks.() -> Unit) = tasks.init()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Configuration

        if (dateTimePattern != other.dateTimePattern) return false
        if (groups != other.groups) return false
        if (tasks != other.tasks) return false
        if (lessonsPerGroup != other.lessonsPerGroup) return false
        if (withDocs != other.withDocs) return false
        if (reposSubDir != other.reposSubDir) return false
        if (reportFile != other.reportFile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateTimePattern.hashCode()
        result = 31 * result + groups.hashCode()
        result = 31 * result + tasks.hashCode()
        result = 31 * result + lessonsPerGroup.hashCode()
        result = 31 * result + withDocs.hashCode()
        result = 31 * result + reposSubDir.hashCode()
        result = 31 * result + (reportFile?.hashCode() ?: 0)
        return result
    }

    companion object {
        lateinit var dateTimeFormatter: DateTimeFormatter
    }
}
