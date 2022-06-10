package ru.nsu.vadim.app

import org.gradle.tooling.GradleConnector
import java.io.File

// Gradle related stuff

/**
 * Pair(testSuccess, buildSuccess)
 */
typealias TestBuild = Pair<Boolean, Boolean>

/**
 * Test and build Gradle project
 */
fun App.testAndBuild(projectDir: String): TestBuild {

    val projectConnection = projectConnection(projectDir, gradleVersion)

    var testRes: Boolean
    var buildRes: Boolean

    projectConnection.use { connection ->

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
}

private fun projectConnection(projectDir: String, gradleVersion: String) = GradleConnector.newConnector()
    .forProjectDirectory(File(projectDir))
    .useGradleVersion(gradleVersion).connect()

fun App.doc(projectDir: String) {
    val projectConnection = projectConnection(projectDir, gradleVersion)

    val build = projectConnection.newBuild()
        .forTasks("javadoc")

    build.run()
}