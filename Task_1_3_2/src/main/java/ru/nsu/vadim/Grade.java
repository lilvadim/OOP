package ru.nsu.vadim;

import java.time.LocalDate;

public class Grade {
    /**
     * initialises Grade with only a grade value
     *
     * @param grade integer grade value
     */
    public Grade(int grade) {
        this.grade = grade;
    }

    /**
     * initialises Grade without Teacher
     *
     * @param grade integer grade value
     * @param date  date of grade
     * @param form  form of attestation
     */
    public Grade(int grade, LocalDate date, FormType form) {
        this.grade = grade;
        this.date = date;
        this.form = form;
    }

    /**
     * Fully initialise Grade
     *
     * @param grade             integer grade value
     * @param date              date of grade
     * @param form              form of attestation
     * @param teacherSurname    teacher's surname
     * @param teacherName       teacher's name
     * @param teacherPatronymic teacher's patronymic
     */
    public Grade(int grade,
                 LocalDate date,
                 FormType form,
                 String teacherSurname,
                 String teacherName,
                 String teacherPatronymic) {
        this(grade, date, form);
        this.teacher = new Person(teacherSurname, teacherName, teacherPatronymic);
    }

    /**
     * Fully initialise Grade using Person to initialise Teacher
     *
     * @param grade   integer grade value
     * @param date    date of grade
     * @param form    form of attestation
     * @param teacher teacher as Person object
     */
    public Grade(int grade, LocalDate date, FormType form, Person teacher) {
        this(grade, date, form);
        this.teacher = teacher;
    }

    private int grade;
    private LocalDate date;

    /**
     * Attestation form: exam, credit, differential credit
     */
    public enum FormType {diffCredit, exam, credit}

    private FormType form;
    private Person teacher;

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

    public FormType getForm() {
        return form;
    }

    public void setForm(FormType form) {
        this.form = form;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }


}
