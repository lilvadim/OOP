package ru.nsu.vadim;

public class Calculator {
    Operation toDo;

    public Calculator(String input) {
        OpertationFabric fab = new OpertationFabric(input);
        toDo = fab.parseString();
    }

    public double execute() {
        return toDo.apply();
    }
}
