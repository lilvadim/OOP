package ru.nsu.vadim;

public class Add extends Operation {
    protected Add(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a + b;
    }
}
