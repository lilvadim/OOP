package ru.nsu.vadim.console.operations;

public class Pow extends Operation {
    public Pow(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return Math.pow(a, b);
    }
}
