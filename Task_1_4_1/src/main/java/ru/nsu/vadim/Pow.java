package ru.nsu.vadim;

public class Pow extends Operation {
    protected Pow(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return Math.pow(a, b);
    }
}
