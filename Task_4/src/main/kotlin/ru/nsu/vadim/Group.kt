package ru.nsu.vadim

typealias Students = MutableList<Student>

class Group(val id: String, private val students: Students = mutableListOf()) : Students by students

class StudentsBuilder(private val students: Students = mutableListOf()) {
    fun student(name: String, config: Student.() -> Unit) {
        val student = Student(name)
        student.config()
        students+=student
    }

    fun build() = students
}

fun Configuration.group(id: String, init: StudentsBuilder.() -> Unit) {
    val studentsBuilder = StudentsBuilder()
    studentsBuilder.init()
    group = Group(id, studentsBuilder.build())
}