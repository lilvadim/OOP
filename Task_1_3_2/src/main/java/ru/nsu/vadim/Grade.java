package ru.nsu.vadim;

import java.time.LocalDate;

public class Grade {
    /**
     * Initialises Grade with only a grade value
     *
     * @param grade integer grade value
     */
    public Grade(int grade) {
        this.grade = grade;
    }

    /**
     * Initialises Grade without Teacher
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

    /**
     * Returns grade integer value
     *
     * @return grade
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Sets grade integer value
     *
     * @param grade grade to set
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Returns date of grade as LocalDate object
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date of grade to given date as LocalDate object
     *
     * @param date date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns form of attestation
     *
     * @return form
     */
    public FormType getForm() {
        return form;
    }

    /**
     * Sets form of attestation
     *
     * @param form form
     */
    public void setForm(FormType form) {
        this.form = form;
    }

    /**
     * Returns teacher as Person class object
     *
     * @return teacher
     */
    public Person getTeacher() {
        return teacher;
    }

    /**
     * Sets teacher to Person class object given as parameter
     *
     * @param teacher teacher to set
     */
    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }


}
