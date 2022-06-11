package ru.nsu.vadim.app

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import ru.nsu.vadim.dsl.Configuration
import ru.nsu.vadim.model.Group
import ru.nsu.vadim.model.Student
import ru.nsu.vadim.model.Task
import java.io.BufferedWriter
import java.io.File
import kotlin.system.exitProcess


const val DEFAULT_GRADLE_VER = "7.4.2"
private const val DEFAULT_CFG_NAME: String = "config.kts"

/**
 * Contains app logic
 */
class App {

    private val argParser: ArgParser = ArgParser("oop-tester")

    private val DEFAULT_CFG_PATH: String by lazy { "$workingDirPath/$DEFAULT_CFG_NAME" }

    val gradleVersion: String = DEFAULT_GRADLE_VER

    val workingDirPath: String by argParser.argument(
        type = ArgType.String,
        fullName = "directory",
        description = "Working directory to lookup config and store cloned repos",
    )

    private val configPath: String? by argParser.option(
        type = ArgType.String,
        fullName = "config",
        shortName = "c",
        description = "Configuration file path to be used instead of default one " +
                "(config.kts in root of working directory)"
    )

    private val gitHubPersonalAccessToken: String? by argParser.option(
        type = ArgType.String,
        fullName = "token",
        shortName = "t",
        description = "Use specified GitHub Personal Access Token to authenticate"
    )

    val configuration: Configuration by lazy {
        val scriptString = File(configPath ?: DEFAULT_CFG_PATH).readText()
        parse(scriptString)
    }

    fun Task.folderName() = (configuration.taskFolderPattern)(this)

    fun Group.folderName() = (configuration.groupFolderPattern)(this)

    fun Student.repoFolderName() = (configuration.studentRepoFolderPattern)(this)

    fun main(args: Array<String>) {
        argParser.parse(args)

        try {
            if (gitHubPersonalAccessToken == null) {
                downloadGitRepos()
            } else {
                downloadGitRepos(token = gitHubPersonalAccessToken!!, useToken = true)
            }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
            exitProcess(1)
        }

        val bufferedWriter = configuration.reportFile?.writer()?.let { BufferedWriter(it) }

        val groupReports = mutableListOf<GroupReport>()
        bufferedWriter.use { fileWriter ->
            for (group in configuration.groups) {
                val perGroupReportResults = mutableListOf<GroupReport.ResultForStudent>()

                for (student in group) {
                    val attendance = attendance(group, student)
                    val perStudentTaskTestBuildResults = mutableMapOf<Task, TestBuild>()
                    for (task in configuration.tasks) {
                        resetToLastCommitBeforeDate(task.deadline, repoPath(group, student))
                        val projectDir = projectPath(repoPath(group, student), task)
                        perStudentTaskTestBuildResults += task to testAndBuild(projectDir)

                        if (configuration.withDocs) {
                            docs(projectDir)
                        }
                    } // for tasks

                    perGroupReportResults += GroupReport.ResultForStudent(
                        student,
                        attendance,
                        perStudentTaskTestBuildResults
                    )
                } // for students

                groupReports += GroupReport(
                    groupID = group.id,
                    totalLessonsCount = configuration.lessonsPerGroup[group]!!.size,
                    testResults = perGroupReportResults,
                )
            } // for groups

            if (configuration.reportFile == null) {
                System.out.append(convertToHtml(groupReports))
            } else {
                fileWriter?.append(convertToHtml(groupReports))
            }
        } // writer use
    } // main
}

/**
 * Full path to student's repo folder
 */
fun App.repoPath(group: Group, student: Student) =
    "$workingDirPath/${configuration.reposSubDir}/${group.folderName()}/${student.repoFolderName()}"

/**
 * Full path to tasks folder in repo
 */
fun App.projectPath(repoPath: String, task: Task) = "$repoPath/${task.folderName()}"

/**
 * User home directory path
 */
fun userHomeDir(): String = System.getProperty("user.home")