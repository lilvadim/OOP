package ru.nsu.vadim.employee;

public abstract class AbstractEmployee implements Employee {

    private final long id;
    private final WorkExperience workExperience;

    public AbstractEmployee(long id, WorkExperience workExperience) {
        this.id = id;
        this.workExperience = workExperience;
    }

    @Override
    public long getId() {
        return id;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " ID=" + getId();
    }
}
