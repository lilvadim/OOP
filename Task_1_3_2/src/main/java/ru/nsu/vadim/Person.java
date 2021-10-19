package ru.nsu.vadim;

public class Person {
    Person(String f, String i, String o) {
        this.setSurname(f);
        this.setName(i);
        this.setPatronymic(o);
    }

    private String surname;      // Ф
    private String name;         // И
    private String patronymic;   // О

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }
}
