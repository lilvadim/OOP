package ru.nsu.vadim.operations;

public class Add extends Operation {
    public Add(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a + b;
    }
}
