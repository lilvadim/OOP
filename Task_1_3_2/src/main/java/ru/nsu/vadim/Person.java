package ru.nsu.vadim;

public class Person {
    /**
     * Initialises Person object
     *
     * @param surname    surname e.g. "Mostovoy"
     * @param name       name e.g. "Vadim"
     * @param patronymic patronymic e.g. "Evgenevich"
     */
    public Person(String surname, String name, String patronymic) {
        this.setSurname(surname);
        this.setName(name);
        this.setPatronymic(patronymic);
    }

    private String surname;      // Ф
    private String name;         // И
    private String patronymic;   // О

    /**
     * Returns name of person
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns surname of person
     *
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns patronymic of person
     *
     * @return patronymic
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets patronymic of person
     *
     * @param patronymic patronymic to set
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Sets name of person
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets surname of person
     *
     * @param surname surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns full name of person as string concat of name, surname and patronymic
     * e.g "Mostovoy Vadim Evgenevich"
     *
     * @return full name of person as one string
     */
    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
