package ru.nsu.vadim;

public class Divide extends Operation {
    protected Divide(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a / b;
    }
}
