package ru.nsu.vadim.app

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import ru.nsu.vadim.dsl.Configuration
import ru.nsu.vadim.model.Student
import ru.nsu.vadim.model.Task
import java.io.File
import kotlin.system.exitProcess


const val DEFAULT_GRADLE_VER = "7.4.2"
const val DEFAULT_CFG_PATH: String = "config.kts"
const val DEFAULT_REPOS_SUBDIR: String = "repos"

/**
 * Contains app logic
 */
class App {

    private val argParser: ArgParser = ArgParser("oop-tester")

    lateinit var configuration: Configuration
        private set

    val workingDirPath: String by argParser.argument(
        type = ArgType.String,
        fullName = "directory",
        description = "Working directory to lookup config and store cloned repos",
    )

    val gradleVersion: String = DEFAULT_GRADLE_VER

    val reposSubDir: String = DEFAULT_REPOS_SUBDIR

    private val configPath: String by argParser.option(
        type = ArgType.String,
        fullName = "config",
        shortName = "c",
        description = "Configuration file path to be used instead of default one (config.kts)"
    ).default(DEFAULT_CFG_PATH)

    val repoName: Student.() -> String by lazy { configuration.studentRepoFolderPattern }

    val folderName: Task.() -> String by lazy { configuration.taskFolderPattern }

    fun main(args: Array<String>) {

        val token by argParser.option(
            type = ArgType.String,
            fullName = "token",
            shortName = "t",
            description = "Use specified GitHub Personal Access Token to authenticate"
        )

        val out by argParser.option(
            type = ArgType.String,
            fullName = "output",
            shortName = "o",
            description = "File to write report"
        )

        val doc by argParser.option(
            type = ArgType.Boolean,
            fullName = "doc",
            shortName = "d",
            description = "Run Gradle Javadoc"
        ).default(false)

        argParser.parse(args)

        val scriptString = File("$workingDirPath/${configPath}").readText()

        configuration = parse(scriptString)

        try {
            if (token == null) {
                downloadGitRepos()
            } else {
                downloadGitRepos(token = token!!, useToken = true)
            }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
            exitProcess(1)
        }

        val reportCollection = mutableListOf<Report.ResultForStudent>()

        for (student in configuration.group) {

            val attendance = attendance(student)

            val taskTestBuildResults = mutableMapOf<Task, TestBuild>()

            for (task in configuration.tasks) {

                resetToLastCommitBeforeDate(task.deadline, repoPath(student))

                val projectDir = projectPath(repoPath(student), task)

                if (doc) {
                    doc(projectDir)
                }

                taskTestBuildResults += task to testAndBuild(projectDir)

            }

            reportCollection += Report.ResultForStudent(
                student,
                attendance,
                taskTestBuildResults
            )
        }

        val report = Report(configuration.group.id, reportCollection, configuration.lessons.size)

        if (out == null) {
            println(report.toHtml())
        } else {
            File(out!!.replaceFirst("~", userHomeDir())).writeText(report.toHtml())
        }
    }
}

/**
 * Full path to student's repo folder
 */
fun App.repoPath(student: Student) = "$workingDirPath/$reposSubDir/${student.repoName()}"

/**
 * Full path to tasks folder in repo
 */
fun App.projectPath(repoPath: String, task: Task) = "$repoPath/${task.folderName()}"

/**
 * User home directory path
 */
fun userHomeDir(): String = System.getProperty("user.home")