package ru.nsu.vadim.console.operations;

public class Sin extends Operation {
    public Sin(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.sin(a);
    }
}
