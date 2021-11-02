package ru.nsu.vadim;

public class Multiply extends Operation {
    protected Multiply(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a * b;
    }
}
