package ru.nsu.vadim.app

import ru.nsu.vadim.model.Student
import ru.nsu.vadim.model.Task

data class Report(
    val groupID: String,
    private val testResults: Collection<ResultForStudent>,
    val totalLessonsCount: Int
) : Collection<Report.ResultForStudent> by testResults {
    data class ResultForStudent(
        val student: Student,
        val attendance: Int,
        val taskTestBuildResults: Map<Task, TestBuild>
    )
}