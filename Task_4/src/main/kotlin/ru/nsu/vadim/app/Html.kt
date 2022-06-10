package ru.nsu.vadim.app

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import ru.nsu.vadim.dsl.Configuration
import java.text.DecimalFormat
import java.time.LocalDate

/**
 * Convert report to HTML
 */
fun Report.toHtml(): String = createHTML().html {
    title = "Report for Group $groupID"
    body {
        h1 { +"Report for Group $groupID (${LocalDate.now().format(Configuration.dateTimeFormatter)})" }
        forEach { res ->
            h2 { +res.student.name }

            h3 { +"Attendance" }
            val df = DecimalFormat.getPercentInstance()
            div {
                +"${res.attendance} / $totalLessonsCount ~ ${
                    df.format(res.attendance / totalLessonsCount.toDouble())
                }"
            }

            h3 { +"Tasks Test & Build Results" }
            for ((task, testBuild) in res.taskTestBuildResults) {
                val (test, build) = testBuild

                h4 { +"${task.description.id} #${task.id} (deadline: ${task.deadline.format(Configuration.dateTimeFormatter)})" }
                div { +"build = ${if (build) "SUCCESS" else "FAILED"}" }
                div { +"test = ${if (test) "SUCCESS" else "FAILED"}" }
            }
        }
    }
}