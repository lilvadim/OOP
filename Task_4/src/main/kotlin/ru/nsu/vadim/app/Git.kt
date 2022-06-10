package ru.nsu.vadim.app

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import ru.nsu.vadim.model.Student
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*

// Git related stuff

/**
 * Clones repos. If already cloned then fetch and then rebase
 */
fun App.downloadGitRepos(token: String = "", useToken: Boolean = false) {

    val credentialsProvider: CredentialsProvider = if (useToken) {
        fromToken(token)
    } else {
        val config = File("${userHomeDir()}/.gitconfig").readText()
        fromConfig(config)
    }

    for (student in configuration.group) {
        val dir = File("$workingDirPath/$reposSubDir/${student.repoName()}")
        if (!dir.exists()) {
            Git.cloneRepository()
                .setCredentialsProvider(credentialsProvider)
                .setURI(student.repositoryUrl)
                .setBranch(student.branch)
                .setDirectory(dir)
                .call()

        } else {
            val repo = Git.open(dir)
            val fetchResult = repo.fetch()
                .setCredentialsProvider(credentialsProvider)
                .call()
            if (!fetchResult.trackingRefUpdates.isEmpty()) {
                repo.reset()
                    .setMode(ResetCommand.ResetType.HARD)
                    .call()
            }
        }
    }
}

private fun fromToken(token: String) = UsernamePasswordCredentialsProvider(token, "")

private fun fromConfig(config: String): CredentialsProvider {
    val userCatIdx = config.indexOf("[user]")
    val nameIdx = config.indexOf(startIndex = userCatIdx, string = "name")
    val passwordIdx = config.indexOf(startIndex = userCatIdx, string = "password")

    if (userCatIdx < 0
        || nameIdx < 0
        || passwordIdx < 0
    ) {
        throw IllegalStateException(
            "Username/password not found in .gitconfig.\n" +
                    "Use git config --global to configure"
        )
    }

    val username = config
        .substring(nameIdx)
        .substringAfter("=")
        .substringBefore("\n")
        .trim()


    val password = config
        .substring(passwordIdx)
        .substringAfter("=")
        .substringBefore("\n")
        .trim()

    return UsernamePasswordCredentialsProvider(username, password)
}

private fun LocalDate.convertToDate() = Date.from(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())!!

/**
 * Count visited lessons according to lessons list and commits
 */
fun App.attendance(student: Student): Int {
    val repo = Git.open(File(repoPath(student)))

    var count = 0

    for ((date) in configuration.lessons) {
        val (since, to) = week(date)

        val revFilter = CommitTimeRevFilter.between(since.convertToDate(), to.convertToDate())

        repo.log()
            .setRevFilter(revFilter)
            .call()
            .also {
                if (it.any()) {
                    count++
                }
            }
    }

    return count
}

/**
 * Start and end days of week that contains date
 */
fun week(date: LocalDate): Pair<LocalDate, LocalDate> {
    val weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))!!
    val weekEnd = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))!!

    return Pair(weekStart, weekEnd)
}

/**
 * Resets local repo to last commit before date
 */
fun resetToLastCommitBeforeDate(date: LocalDate, repoPath: String) {
    val repo = Git.open(File(repoPath))

    val commits = repo.log()
        .setRevFilter(CommitTimeRevFilter.before(date.convertToDate()))
        .call()
        .toList()
        .reversed()

    repo.reset()
        .setMode(ResetCommand.ResetType.HARD)
        .setRef(commits.last().toObjectId().name)
        .call()
}