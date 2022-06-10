package ru.nsu.vadim.app

import ru.nsu.vadim.model.Student
import ru.nsu.vadim.model.Task

data class GroupReport(
    val groupID: String,
    val totalLessonsCount: Int,
    private val testResults: Collection<ResultForStudent>,
) : Collection<GroupReport.ResultForStudent> by testResults {
    data class ResultForStudent(
        val student: Student,
        val attendance: Int,
        val taskTestBuildResults: Map<Task, TestBuild>
    )

    fun score(student: Student) =
        testResults.find { it.student == student }
            ?.taskTestBuildResults
            ?.values
            ?.sumOf { if (it.first && it.second) 1 else 0 as Int }
}