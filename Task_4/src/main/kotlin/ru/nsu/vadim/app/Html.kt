package ru.nsu.vadim.app

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import ru.nsu.vadim.dsl.Configuration
import java.text.DecimalFormat
import java.time.LocalDate

/**
 * Convert report to HTML document
 */
fun GroupReport.toHtml(): String = createHTML().html {
    body {
        style {
            unsafe {
                raw(
                    ".green {"
                            + "color: green;"
                            + "}"
                            + ".red {"
                            + "color: red;"
                            + "}"
                )
            }
        }
        h1 { +"Group $groupID" }
        forEach { res ->
            h2 { +res.student.name }

            val df = DecimalFormat.getPercentInstance()
            h3 { +"Attendance [${res.attendance} / $totalLessonsCount] ~ ${df.format(res.attendance / totalLessonsCount.toDouble())}" }

            val score = score(res.student)
            val totalTasksCount = res.taskTestBuildResults.keys.size
            h3(
                if (score == totalTasksCount) {
                    "green"
                } else {
                    "red"
                }
            ) { +"Tasks Test & Build Results [$score / $totalTasksCount]" }
            for ((task, testBuild) in res.taskTestBuildResults) {
                val (test, build) = testBuild

                h4 { +"${task.description.id} #${task.id} (deadline: ${task.deadline.format(Configuration.dateTimeFormatter)})" }
                div(
                    if (build) {
                        "green"
                    } else {
                        "red"
                    }
                ) { +"build = ${if (build) "SUCCESS" else "FAILED"}" }

                div(
                    if (test) {
                        "green"
                    } else {
                        "red"
                    }
                ) { +"test = ${if (test) "SUCCESS" else "FAILED"}" }
            }
        }
    }
}

/**
 * Converts list of reports to HTML document using `GroupReport.toHtml()`
 */
fun convertToHtml(list: Collection<GroupReport>) = createHTML().html {
    body {
        h1 { +"Report ${LocalDate.now().format(Configuration.dateTimeFormatter)}" }
        list.forEach { unsafe { +it.toHtml() } }
    }
}