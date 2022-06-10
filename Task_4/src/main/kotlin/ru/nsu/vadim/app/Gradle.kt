package ru.nsu.vadim.app

import org.gradle.tooling.GradleConnector
import java.io.File

// Gradle related stuff

/**
 * Pair(testSuccess, buildSuccess)
 */
typealias TestBuild = Pair<Boolean, Boolean>

fun App.testAndBuild(projectDir: String): TestBuild = projectConnection(projectDir).use { connection ->

    val testRes: Boolean
    val buildRes: Boolean

    var build = connection.newBuild().forTasks("build")

    buildRes = try {
        build.run()
        true
    } catch (ex: Exception) {
        println(ex.localizedMessage)
        false
    }

    build = connection.newBuild().forTasks("test")

    testRes = try {
        build.run()
        true
    } catch (ex: Exception) {
        println(ex.localizedMessage)
        false
    }

    return TestBuild(testRes, buildRes)
}


private fun App.projectConnection(projectDir: String) =
    GradleConnector.newConnector()
        .forProjectDirectory(File(projectDir))
        .useGradleVersion(gradleVersion).connect()

fun App.docs(projectDir: String) = projectConnection(projectDir).use { connection ->
    connection.newBuild().forTasks("javadoc").run()
}