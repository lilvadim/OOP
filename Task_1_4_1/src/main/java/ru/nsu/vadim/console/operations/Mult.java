package ru.nsu.vadim.console.operations;

public class Mult extends Operation {
    public Mult(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a * b;
    }
}
