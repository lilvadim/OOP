package ru.nsu.vadim;

import java.time.LocalDate;

public class Grade {
    Grade(int grade) {
        this.grade = grade;
    }
    Grade(int grade, LocalDate date, formType form) {
        this.grade = grade;
        this.date = date;
        this.form = form;
    }

    Grade(int grade, LocalDate date, formType form,
          String teacherF,
          String teacherI,
          String teacherO) {
        this(grade, date, form);
        this.teacher.setSurname(teacherF);
        this.teacher.setName(teacherI);
        this.teacher.setPatronymic(teacherO);
    }

    Grade(int grade, LocalDate date, formType form, Person teacher) {
        this(grade, date, form);
        this.teacher = teacher;
    }

    private int grade;
    private LocalDate date = null;
    public enum formType {diffCredit, exam, credit}
    private formType form = null;
    private Person teacher = null;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public formType getForm() {
        return form;
    }

    public void setForm(formType form) {
        this.form = form;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }


}
