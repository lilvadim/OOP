package ru.nsu.vadim;

public class Person {
    public Person(String surname, String name, String patronymic) {
        this.setSurname(surname);
        this.setName(name);
        this.setPatronymic(patronymic);
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

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
