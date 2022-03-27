package ru.nsu.vadim.employee;

public enum WorkExperience {
    PROBATIONER(1),
    LESS_THAN_YEAR(2),
    YEAR_OR_MORE(3),
    TWO_YEARS_OR_MORE(4),
    EXPERT(5);

    private final long workSpeedLevel;
    private final long timeOfCompletingTask;

    WorkExperience(long workSpeedLevel) {
        this.workSpeedLevel = workSpeedLevel;
        timeOfCompletingTask = 10000 / workSpeedLevel;
    }

    public long workSpeedLevel() {
        return workSpeedLevel;
    }

    public long timeOfCompletingTask() {
        return timeOfCompletingTask;
    }
}
